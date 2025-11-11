package org.gpc4j.sba.server;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHashGeneratorTest {

    @Test
    void printBcrypt() {
        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String raw = "alt01ds!";
        String hash = encoder.encode(raw);
        System.out.println("[DEBUG_LOG] BCRYPT_HASH=" + hash);
        // Not asserting anything; this test just prints the hash for manual use.
    }
}
