package com.memory.beautifulbride.entitys.member;

import com.memory.beautifulbride.entitys.logindata.AbstractLoginDataMapping;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "TBL_MEMBER")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractLoginDataMapping {

    @Id
    @Column(name = "MEM_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int memNo = 0;

    // LOGIN :: AbstractNeedDate

    @OneToOne(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "PROFILE_INDEX")
    private Profile profile;

/*    @Component
    @RequiredArgsConstructor
    @org.springframework.context.annotation.Profile("dev")
    static class DataInit implements CommandLineRunner {
        private final LoginDataRepository loginDataRepository;
        private final LoginDataCommandService loginDataCommandService;

        @Override
        public void run(String... args) throws Exception {
            if (loginDataRepository.findById(1).isEmpty()) {
                MemberSignupDTO memberSignupDTO = MemberSignupDTO.builder()
                        .loginId("membertest1")
                        .loginPwd("membertest1")
                        .loginEmail("membertest@test.com")
                        .memPhone("010-1234-1234")
                        .memWeddingDate("2023-12-15")
                        .build();
                loginDataCommandService.createMemberAccount(memberSignupDTO);
            }
        }
    }*/
}
