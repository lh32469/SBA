package org.gpc4j.sba.server;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class EncryptionIT {

  @Autowired
  StringEncryptor jasyptStringEncryptor;

  @Test
  void jasypt() {

    String encrypted = jasyptStringEncryptor.encrypt("EncryptMessage");
    System.out.println("Encrypted: " + encrypted);

    // Test decryption
    String decrypted = jasyptStringEncryptor.decrypt(encrypted);
    System.out.println("Decrypted: " + decrypted);
  }

}
