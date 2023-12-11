package com.memory.beautifulbride.service.logindata;

import com.memory.beautifulbride.entitys.basics.BasicsKinds;
import com.memory.beautifulbride.entitys.basics.KindsTBL;
import com.memory.beautifulbride.entitys.basics.LoginData;
import com.memory.beautifulbride.dtos.LoginDataRestPwdDto;
import com.memory.beautifulbride.entitys.company.Company;
import com.memory.beautifulbride.entitys.member.Member;
import com.memory.beautifulbride.entitys.member.Profile;
import com.memory.beautifulbride.dtos.CompanySignupDTO;
import com.memory.beautifulbride.dtos.MemberSignupDTO;
import com.memory.beautifulbride.repository.basics.LoginDataRepository;
import com.memory.beautifulbride.repository.company.CompanyRepository;
import com.memory.beautifulbride.repository.member.MemberRepository;
import com.memory.beautifulbride.repository.member.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class LoginDataCommanService {

    private final CompanyRepository companyRepository;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final LoginDataRepository loginDataRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> createCompanyAccount(CompanySignupDTO dto) {
        LoginData loginData = LoginData.builder()
                .loginId(dto.loginId())
                .loginPwd(dto.loginPwd())
                .loginEmail(dto.loginEmail())
                .kinds(KindsTBL.builder().basicsKinds(BasicsKinds.COMPANY).build())
                .build();

        try {
            companyRepository.save(
                    Company.builder()
                            .loginData(loginData)
                            .companyName(dto.companyName())
                            .companyPhone(dto.companyPhone())
                            .address(dto.address())
                            .businessNumber(dto.businessNumber())
                            .build()
            );

            return ResponseEntity.ok().body("계정 생성에 성공하셨습니다");
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.unprocessableEntity().body("계정 생성에 살패하셨습니다.");
        }
    }

    public ResponseEntity<String> createMemberAccount(MemberSignupDTO dto) {
        LoginData loginData = LoginData.builder()
                .loginId(dto.loginId())
                .loginPwd(dto.loginPwd())
                .loginEmail(dto.loginEmail())
                .kinds(KindsTBL.builder()
                        .basicsKinds(BasicsKinds.FREE)
                        .build())
                .build();

        Member member = Member.builder().loginData(loginData).build();

        try {
            profileRepository.save(
                    Profile.builder()
                            .member(member)
                            .memPhone(dto.memPhone())
                            .memName(dto.memName())
                            .memWeddingDate(
                                    Date.valueOf(LocalDate.parse(dto.memWeddingDate().trim()))
                            )
                            .build()
            );

            return ResponseEntity.ok().body("계정 생성에 성공하셨습니다.");
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.unprocessableEntity().body("계정 생성에 실패하셨습니다.");
        }
    }

    public ResponseEntity<String> resetAccountPwd(LoginDataRestPwdDto dto) {

        LoginData loginData = loginDataRepository.findByLoginData(dto.loginId(), dto.loginEmail());

        if (loginData == null) {
            new ResponseEntity<>("계정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        return null;
    }
}
