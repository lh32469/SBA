package org.gpc4j.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author Lyle T Harris (lyle.harris@gmail.com)
 */
@SpringBootApplication
@EnableAdminServer
public class ApplicationMain {


  static Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);

  public static void main(String[] args) throws UnknownHostException {

    // Add hostname to Mapped Diagnostic Context for Logback XML file variables.
    MDC.put("hostname", InetAddress.getLocalHost().getHostName());

    LOG.info("Starting..  args = " + Arrays.toString(args));

    SpringApplication.run(ApplicationMain.class, args);
  }

}
