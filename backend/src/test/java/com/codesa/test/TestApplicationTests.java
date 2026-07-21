package com.codesa.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "jwt.secret=12345678901234567890123456789012",
        "jwt.expiration-ms=86400000"
})
class TestApplicationTests {

    @Test
    void contextLoads() {
    }

}
