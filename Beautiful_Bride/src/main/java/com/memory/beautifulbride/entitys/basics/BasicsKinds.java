package com.memory.beautifulbride.entitys.basics;

import com.memory.beautifulbride.config.AbstractEnumToString;
import com.memory.beautifulbride.repository.basics.KindsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

public enum BasicsKinds {
    FREE, CHARGED, COMPANY, ADMIN;

    public static class Convert extends AbstractEnumToString<BasicsKinds> {
        public Convert() {
            super(BasicsKinds.class);
        }
    }

    @Component
    @RequiredArgsConstructor
    public static class DataInit implements CommandLineRunner {

        private final KindsRepository repository;

        @Override
        public void run(String... args) throws Exception {
            Arrays.stream(BasicsKinds.values())
                    .filter(basicsKinds -> !repository.existsByBasicsKinds(basicsKinds))
                    .map(KindsTBL::new)
                    .forEach(repository::save);
        }
    }
}
