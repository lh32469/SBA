package org.gpc4j.admin;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class TrustAllCerts implements X509TrustManager {

  @Override
  public void checkClientTrusted(X509Certificate[] xcs, String string) {
  }

  @Override
  public void checkServerTrusted(X509Certificate[] xcs, String string) {
  }

  @Override
  public X509Certificate[] getAcceptedIssuers() {
    return null;
  }

}
