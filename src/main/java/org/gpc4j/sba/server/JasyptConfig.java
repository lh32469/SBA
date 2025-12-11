package org.gpc4j.sba.server;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {
  // This ensures Jasypt is initialized early

  @Bean("jasyptStringEncryptor")
  public StringEncryptor stringEncryptor() {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    encryptor.setPassword(System.getenv("JASYPT_ENCRYPTOR_PASSWORD")); // Get from env var
//    System.out.println("=========================================");
//    System.out.println(System.getenv("JASYPT_ENCRYPTOR_PASSWORD"));


    encryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
    encryptor.setIvGenerator(new RandomIvGenerator());
    encryptor.setPoolSize(1);
    return encryptor;
  }

}
