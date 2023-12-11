package com.memory.beautifulbride;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeautifulBrideApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeautifulBrideApplication.class, args);
    }
}
