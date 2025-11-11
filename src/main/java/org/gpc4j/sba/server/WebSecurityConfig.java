package org.gpc4j.sba.server;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  private final AdminServerProperties adminServer;

  public WebSecurityConfig(AdminServerProperties adminServer) {
    this.adminServer = adminServer;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    String adminContextPath = adminServer.getContextPath();

    SavedRequestAwareAuthenticationSuccessHandler successHandler =
        new SavedRequestAwareAuthenticationSuccessHandler();
    successHandler.setTargetUrlParameter("redirectTo");
    successHandler.setDefaultTargetUrl(adminContextPath + "/");

    System.out.println("[DEBUG_LOG] adminContextPath=" + adminContextPath);
    http
        .csrf(csrf -> csrf
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringRequestMatchers(
                // Allow the admin server to register instances and access actuator
                request ->
                    matchesPath(request.getRequestURI(), adminContextPath + "/instances"),
                request ->
                    matchesPath(request.getRequestURI(),
                                adminContextPath + "/instances/*"),
                request ->
                    matchesPath(request.getRequestURI(),
                                adminContextPath + "/actuator/**")
            )
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                adminContextPath + "/assets/**",
                adminContextPath + "/login",
                adminContextPath + "/actuator/health/**"
            ).permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage(adminContextPath + "/login")
            .successHandler(successHandler)
        )
        .logout(logout ->
                    logout.logoutUrl(adminContextPath + "/logout"))
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
