package com.memory.beautifulbride.entitys.logindata;

import com.memory.beautifulbride.config.custom.AbstractEnumToString;

public enum BasicsKinds {
    FREE, CHARGED, COMPANY, ADMIN;

    public static class Convert extends AbstractEnumToString<BasicsKinds> {
        public Convert() {
            super(BasicsKinds.class);
        }
    }


/*    @Component
    @RequiredArgsConstructor
    public static class BasicsKindsDataInit implements CommandLineRunner {

        private final KindsRepository initRepository;

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
    }*/
}
