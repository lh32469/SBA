package org.gpc4j.sba.server;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  static final String REMEMBER_ME_COOKIE = "D24E3843-1748-469E-AED5-ED394D8032A6";

  private final AdminServerProperties adminServer;

  public WebSecurityConfig(AdminServerProperties adminServer) {
    this.adminServer = adminServer;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    String adminContextPath = adminServer.getContextPath();
    log.info("adminContextPath = ' " + adminContextPath + "'");

    SavedRequestAwareAuthenticationSuccessHandler successHandler =
        new SavedRequestAwareAuthenticationSuccessHandler();
    successHandler.setTargetUrlParameter("redirectTo");
    successHandler.setDefaultTargetUrl(adminContextPath + "/");

    http
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringRequestMatchers(
                // Allow the admin server to register instances and access actuator
                request ->
                    matchesPath(request.getRequestURI(), adminContextPath + "/instances"),
                request ->
                    matchesPath(request.getRequestURI(),
                                adminContextPath + "/instances/**"),
                request ->
                    matchesPath(request.getRequestURI(),
                                adminContextPath + "/actuator/**")
            )
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                adminContextPath + "/assets/**",
                adminContextPath + "/login",
                adminContextPath + "/actuator/**",
                adminContextPath + "/instances/**"
            ).permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage(adminContextPath + "/login")
            .successHandler(successHandler)
        )
        .rememberMe(remember -> remember
            .key("uniqueAndSecret") // Change this to your own secret key
            .tokenValiditySeconds(1209600) // 14 days
            .rememberMeParameter("remember-me") // Match your checkbox name
            .rememberMeCookieName(REMEMBER_ME_COOKIE)
        )
        .logout(logout ->
                    logout.logoutUrl(adminContextPath + "/logout")
                          .deleteCookies(REMEMBER_ME_COOKIE))
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }

  private boolean matchesPath(String requestUri, String pattern) {
    // simple helper for "startsWith" or exact match patterns
    if (pattern.endsWith("/**")) {
      String base = pattern.substring(0, pattern.length() - 3);
      return requestUri.startsWith(base);
    }
    if (pattern.endsWith("/*")) {
      String base = pattern.substring(0, pattern.length() - 2);
      if (!requestUri.startsWith(base)) {
        return false;
      }
      String rest = requestUri.substring(base.length());
      return !rest.contains("/");
    }
    return requestUri.equals(pattern);
  }

}
