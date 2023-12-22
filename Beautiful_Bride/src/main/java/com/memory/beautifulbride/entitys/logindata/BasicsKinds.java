package com.memory.beautifulbride.entitys.logindata;

import com.memory.beautifulbride.config.custom.AbstractEnumToString;
import com.memory.beautifulbride.repository.logindata.LoginDataKindsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
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
    @Order(1)
    public static class BasicsKindsDataInit implements CommandLineRunner {

        private final LoginDataKindsRepository initRepository;

        @Override
        public void run(String... args) throws Exception {
            List<KindsTBL> tblList = initRepository.findAll();
            Arrays.stream(BasicsKinds.values()).filter(bEnum ->
                            tblList.stream().noneMatch(kindsTBL ->
                                    kindsTBL.getBasicsKinds().toString().equals(bEnum.name())
                            ))
                    .map(KindsTBL::new)
                    .forEach(initRepository::save);
        }
    }
}
