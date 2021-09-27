package org.gpc4j.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * @author Lyle T Harris (lyle.harris@gmail.com)
 */
@SpringBootApplication
@EnableAdminServer
public class ApplicationMain  {


  static Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);

  public static void main(String[] args) throws UnknownHostException {

    // Add hostname to Mapped Diagnostic Context for Logback XML file variables.
    MDC.put("hostname", InetAddress.getLocalHost().getHostName());

    LOG.info("Starting..  args = " + Arrays.toString(args));

    SpringApplication.run(ApplicationMain.class, args);
  }

  @Bean
  public RestTemplate restTemplate()
      throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
        .loadTrustMaterial(null, acceptingTrustStrategy)
        .build();

    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

    CloseableHttpClient httpClient = HttpClients.custom()
        .setSSLSocketFactory(csf)
        .build();

    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory();

    requestFactory.setHttpClient(httpClient);
    RestTemplate restTemplate = new RestTemplate(requestFactory);
    return restTemplate;
  }
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    LOG.info("============================= ApplicationMain.configure");
//    http.addFilterAfter(
//        new CustomFilter(), BasicAuthenticationFilter.class);
//  }
//
//  public class CustomFilter extends GenericFilterBean {
//
//    @Override
//    public void doFilter(
//        ServletRequest request,
//        ServletResponse response,
//        FilterChain chain) throws IOException, ServletException {
//      LOG.info("CustomFilter.doFilter");
//      LOG.info("request = " + request);
//      chain.doFilter(request, response);
//    }
//  }
}
