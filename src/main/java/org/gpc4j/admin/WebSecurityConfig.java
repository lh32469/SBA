package org.gpc4j.admin;

import de.codecentric.boot.admin.server.config.AdminServerProperties;

//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
public class WebSecurityConfig {

  private final AdminServerProperties adminServer;

  public WebSecurityConfig(AdminServerProperties adminServer) {
    this.adminServer = adminServer;
  }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    SavedRequestAwareAuthenticationSuccessHandler successHandler =
//        new SavedRequestAwareAuthenticationSuccessHandler();
//    successHandler.setTargetUrlParameter("redirectTo");
//    successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/");
//
//    http
//        .authorizeRequests()
//        .antMatchers(this.adminServer.getContextPath() + "/assets/**").permitAll()
//        .antMatchers(this.adminServer.getContextPath() + "/login").permitAll()
//        .anyRequest().authenticated()
//        .and()
//        .formLogin()
//        .loginPage(this.adminServer.getContextPath() + "/login")
//        .successHandler(successHandler)
//        .and()
//        .logout()
//        .logoutUrl(this.adminServer.getContextPath() + "/logout")
//        .and()
//        .httpBasic()
//        .and()
//        .csrf()
//        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//        .ignoringRequestMatchers(
//            new AntPathRequestMatcher(this.adminServer.getContextPath() +
//                "/instances", HttpMethod.POST.toString()),
//            new AntPathRequestMatcher(this.adminServer.getContextPath() +
//                "/instances/*", HttpMethod.DELETE.toString()),
//            new AntPathRequestMatcher(this.adminServer.getContextPath() + "/actuator/**"))
//        .and()
//        .rememberMe()
//        .key(UUID.randomUUID().toString())
//        .tokenValiditySeconds(1209600);
//  }

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
