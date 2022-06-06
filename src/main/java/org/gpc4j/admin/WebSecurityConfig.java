package org.gpc4j.admin;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final AdminServerProperties adminServer;

  public WebSecurityConfig(AdminServerProperties adminServer) {
    this.adminServer = adminServer;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    SavedRequestAwareAuthenticationSuccessHandler successHandler =
        new SavedRequestAwareAuthenticationSuccessHandler();
    successHandler.setTargetUrlParameter("redirectTo");
    successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/");

    http
        .authorizeRequests()
        .antMatchers(this.adminServer.getContextPath() + "/assets/**").permitAll()
        .antMatchers(this.adminServer.getContextPath() + "/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage(this.adminServer.getContextPath() + "/login")
        .successHandler(successHandler)
        .and()
        .logout()
        .logoutUrl(this.adminServer.getContextPath() + "/logout")
        .and()
        .httpBasic()
        .and()
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .ignoringRequestMatchers(
            new AntPathRequestMatcher(this.adminServer.getContextPath() +
                "/instances", HttpMethod.POST.toString()),
            new AntPathRequestMatcher(this.adminServer.getContextPath() +
                "/instances/*", HttpMethod.DELETE.toString()),
            new AntPathRequestMatcher(this.adminServer.getContextPath() + "/actuator/**"))
        .and()
        .rememberMe()
        .key("84766f68-1698-4e76-a9e9-47ff03a7693d")
        .tokenValiditySeconds(1209600);
  }

//
//  @Bean
//  public RestTemplate restTemplate()
//      throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
//
//    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
//        .loadTrustMaterial(null, acceptingTrustStrategy)
//        .build();
//
//    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
//
//    CloseableHttpClient httpClient = HttpClients.custom()
//        .setSSLSocketFactory(csf)
//        .build();
//
//    HttpComponentsClientHttpRequestFactory requestFactory =
//        new HttpComponentsClientHttpRequestFactory();
//
//    requestFactory.setHttpClient(httpClient);
//    RestTemplate restTemplate = new RestTemplate(requestFactory);
//    return restTemplate;
//  }

//  @Bean
//  @Override
//  public UserDetailsService userDetailsService() {
//    System.out.println("------------------------ WebSecurityConfig.userDetailsService");
//    UserDetails user =
//        User.withDefaultPasswordEncoder()
//            .username("user")
//            .password("password")
//            .roles("USER")
//            .build();
//
//    return new InMemoryUserDetailsManager(user);
//  }

}
