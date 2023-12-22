package com.memory.beautifulbride.dummys;

import com.memory.beautifulbride.dtos.company.CompanySignupDTO;
import com.memory.beautifulbride.dtos.member.MemberSignupDTO;
import com.memory.beautifulbride.repository.company.CompanyRepository;
import com.memory.beautifulbride.repository.member.MemberRepository;
import com.memory.beautifulbride.service.logindata.LoginDataCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Profile("dev")
@Order(10000)
public class LoginDataDummy implements CommandLineRunner {
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final LoginDataCommandService loginDataCommandService;

    @Override
    public void run(String... args) throws Exception {
        if (!memberRepository.existsAny()) {
            for (int i = 0; i < 30; i++) {
                MemberSignupDTO dto = MemberSignupDTO.builder()
                        .loginId("member%d".formatted(i))
                        .loginPwd("member%d".formatted(i))
                        .loginEmail("memberemail%d".formatted(i))
                        .memPhone("010-1111-111%d".formatted(i))
                        .memName("membername%d".formatted(i))
                        .memWeddingDate(Date.valueOf(LocalDate.now()))
                        .build();

                loginDataCommandService.createMemberAccount(dto);
            }
        }
        
        if (!companyRepository.existsAny()) {
            for (int i = 0; i < 30; i++) {
                CompanySignupDTO dto = CompanySignupDTO.builder()
                        .loginId("company%d".formatted(i))
                        .loginPwd("company%d".formatted(i))
                        .loginEmail("companyemail%d".formatted(i))
                        .companyName("companyname%d".formatted(i))
                        .companyPhone("010-2222-222%d".formatted(i))
                        .businessNumber("company%d".formatted(i))
                        .address("company%d".formatted(i))
                        .build();

                loginDataCommandService.createCompanyAccount(dto);
            }
        }
    }
}
