package com.memory.beautifulbride.entitys.logindata;

import com.memory.beautifulbride.config.custom.AbstractEnumToString;
import com.memory.beautifulbride.repository.logindata.KindsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

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
            List<KindsTBL> tblList = repository.findAll();
            Arrays.stream(BasicsKinds.values()).filter(bEnum ->
                            tblList.stream().noneMatch(kindsTBL ->
                                    kindsTBL.getBasicsKinds().toString().equals(bEnum.name())
                            ))
                    .map(KindsTBL::new)
                    .forEach(repository::save);
        }
    }
}
