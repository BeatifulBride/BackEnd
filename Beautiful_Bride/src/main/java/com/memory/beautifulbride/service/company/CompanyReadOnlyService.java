package com.memory.beautifulbride.service.company;

import com.memory.beautifulbride.dtos.company.CompanyMainPage;
import com.memory.beautifulbride.entitys.company.Company;
import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.repository.company.CompanyRepository;
import com.memory.beautifulbride.repository.dress.DressInfoRepository;
import com.memory.beautifulbride.repository.logindata.LoginDataRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class CompanyReadOnlyService {

    private final DressInfoRepository dressRepository;
    private final CompanyRepository companyRepository;
    private final LoginDataRepository loginDataRepository;

    public ResponseEntity<CompanyMainPage> getCompanyMainPageInfo(String companyLoginId) {
        LoginData loginData = loginDataRepository.findByLoginId(companyLoginId)
                .filter(LoginData::isCompanyMember)
                .orElseThrow(() -> new RuntimeException("업체 유저가 아닙니다."));

        Company company = companyRepository.searchFromLoginData(companyLoginId)
                .orElseThrow(() -> new EntityNotFoundException("업체 정보를 찾지 못했습니다."));

        try {
            CompanyMainPage dto = CompanyMainPage.builder()
                    .companyName(company.getCompanyName())
                    .companyEmail(loginData.getLoginEmail())
                    .companyAddress(company.getAddress())
                    .companyPhone(company.getCompanyPhone())
                    .dressAllRegistrationCount(companyRepository.dressAllRegistrationCount(company))
                    .latestUpload(companyRepository.findThisCompanyDress(company))
                    .companyTop5DressList(companyRepository.findThisCompanyTop5DressList(company))
                    .companyAllDressList(companyRepository.findThisCompanyAllDressList(company))
                    .build();

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("ComapnyMyPageDTO 생성에 실패했습니다. {}", e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
