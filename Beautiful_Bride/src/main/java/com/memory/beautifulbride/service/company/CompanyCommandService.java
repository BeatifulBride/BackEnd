package com.memory.beautifulbride.service.company;


import com.memory.beautifulbride.dtos.company.CompanyMyPageDTO;
import com.memory.beautifulbride.entitys.company.Company;
import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.repository.company.CompanyRepository;
import com.memory.beautifulbride.repository.logindata.LoginDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class CompanyCommandService {

    private final CompanyRepository companyRepository;
    private final LoginDataRepository loginDataRepository;

    public ResponseEntity<String> companyInfoModify(String companyId, CompanyMyPageDTO companyMyPageDTO) {
        LoginData loginData = loginDataRepository.findByLoginId(companyId)
                .filter(LoginData::isCompanyMember)
                .orElseThrow(() -> new RuntimeException("업체 회원이 아닙니다."))
                .toBuilder()
                .loginEmail(companyMyPageDTO.companyEmail())
                .build();

        Company company = companyRepository.searchFromLoginData(companyId)
                .toBuilder()
                .address(companyMyPageDTO.companyAddress())
                .companyName(companyMyPageDTO.companyName())
                .companyPhone(companyMyPageDTO.companyPhone())
                .build();

        try {
            loginDataRepository.save(loginData);
            companyRepository.save(company);
            return ResponseEntity.ok("업체 정보를 수정 하는데 성공 하였습니다.");
        } catch (DataAccessException e) {
            log.error("업체 정보를 수정하는데 실패했습니다. {}", e.getMessage());
            return ResponseEntity.unprocessableEntity().body("업체 정보를 수정하는데 실패했습니다.");
        }
    }
}
