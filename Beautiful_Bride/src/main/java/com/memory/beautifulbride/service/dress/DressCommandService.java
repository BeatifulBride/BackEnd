package com.memory.beautifulbride.service.dress;

import com.memory.beautifulbride.dtos.dress.DressNewRegistrationDTO;
import com.memory.beautifulbride.entitys.company.Company;
import com.memory.beautifulbride.repository.company.CompanyRepository;
import com.memory.beautifulbride.repository.dress.DressInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class DressCommandService {

    private final DressInfoRepository dressInfoRepository;
    private final CompanyRepository companyRepository;

    public ResponseEntity<String> dressNewRegistration(Principal principal, DressNewRegistrationDTO dto) {

        Company company = companyRepository.searchFromLoginData(principal.getName());

/*        DressInfo newDressInfo = DressInfo.builder()
                .company(company)
                .designer()
                .dressName()
                .dressPNumber()
                .dressPrice()
                .uploadDate()
                .dressMarkCount()
                .dressImagePath()
                .dressDetailsInfo()
                .build();*/
        return null;
    }
}
