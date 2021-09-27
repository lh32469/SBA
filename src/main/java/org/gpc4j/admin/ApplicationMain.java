package org.gpc4j.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Lyle T Harris (lyle.harris@gmail.com)
 */
@SpringBootApplication
@EnableAdminServer
public class ApplicationMain {

  static Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);

//  public static void turnOffSslChecking() throws
//      NoSuchAlgorithmException, KeyManagementException {
//
//    // Install the all-trusting trust manager
//    final SSLContext sc = SSLContext.getInstance("SSL");
//    sc.init(null, new TrustManager[]{new TrustAllCerts()}, null);
//    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//  }

  public static void main(String[] args) throws Exception {

    // Add hostname to Mapped Diagnostic Context for Logback XML file variables.
    MDC.put("hostname", InetAddress.getLocalHost().getHostName());

    LOG.info("Starting..  args = " + Arrays.toString(args));

//    turnOffSslChecking();
    SpringApplication.run(ApplicationMain.class, args);
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
