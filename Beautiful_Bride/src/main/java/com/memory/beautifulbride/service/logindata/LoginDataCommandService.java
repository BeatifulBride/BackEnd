package com.memory.beautifulbride.service.logindata;

import com.memory.beautifulbride.dtos.company.CompanySignupDTO;
import com.memory.beautifulbride.dtos.logindata.LoginDataRestPwdDTO;
import com.memory.beautifulbride.dtos.member.MemberSignupDTO;
import com.memory.beautifulbride.entitys.logindata.BasicsKinds;
import com.memory.beautifulbride.entitys.logindata.KindsTBL;
import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.entitys.company.Company;
import com.memory.beautifulbride.entitys.member.Member;
import com.memory.beautifulbride.entitys.member.Profile;
import com.memory.beautifulbride.repository.logindata.LoginDataRepository;
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
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class LoginDataCommandService {

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
        KindsTBL kindsTBL = new KindsTBL(BasicsKinds.FREE);

        Member member = Member.builder()
                .loginData(LoginData.builder()
                        .loginId(dto.loginId())
                        .loginPwd(dto.loginPwd())
                        .loginEmail(dto.loginEmail())
                        .kinds(kindsTBL).build()
                ).build();

        Profile newProfile = Profile.builder()
                .member(member)
                .memPhone(dto.memPhone())
                .memName(dto.memName())
                .build();

        try {
            if (dto.memWeddingDate() != null) {
                newProfile = newProfile.toBuilder()
                        .memWeddingDate(Date.valueOf(dto.memWeddingDate()))
                        .build();
            }

            profileRepository.save(newProfile);
            return ResponseEntity.ok().body("계정 생성에 성공하셨습니다.");
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.unprocessableEntity().body("계정 생성에 실패하셨습니다.");
        }
    }

    public ResponseEntity<String> resetAccountPwd(LoginDataRestPwdDTO dto) {
        return loginDataRepository.findByLoginData(dto.loginId(), dto.loginEmail())
                .map(x -> {
                    try {
                        loginDataRepository.resetByLoginPwd(
                                dto.loginId(),
                                dto.loginEmail(),
                                passwordEncoder.encode(dto.newPwd())
                        );
                    } catch (DataAccessException e) {
                        return ResponseEntity.unprocessableEntity().body("비밀번호 변경에 실패하셨습니다.");
                    }
                    return ResponseEntity.ok().body("비밀번호 변경에 성공하셨습니다.");
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "계정을 찾을 수 없습니다.")
                );
    }
}
