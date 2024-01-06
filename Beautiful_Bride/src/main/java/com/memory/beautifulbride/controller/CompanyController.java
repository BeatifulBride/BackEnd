package com.memory.beautifulbride.controller;

import com.memory.beautifulbride.dtos.company.CompanyMyPageDTO;
import com.memory.beautifulbride.service.company.CompanyCommandService;
import com.memory.beautifulbride.service.company.CompanyReadOnlyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/com")
public class CompanyController {
    private final CompanyReadOnlyService companyReadOnlyService;
    private final CompanyCommandService companyCommandService;

    @PostMapping("/mypage")
    @Operation(summary = "업체 마이 페이지 정보 입니다.")
    ResponseEntity<CompanyMyPageDTO> myPage(Principal principal) {
        return null;
    }

    @PutMapping("/mypage/modify")
    @Operation(summary = "업체 마이 페이지 정보 수정 입니다.")
    ResponseEntity<String> myPageModify(Principal principal, CompanyMyPageDTO companyMyPageDTO) {
        return companyCommandService.companyInfoModify(principal.getName(), companyMyPageDTO);
    }

    @PutMapping("/mypage/modifyTest")
    @Operation(summary = "업체 마이 페이지 정보 수정 테스트 입니다.")
    @Profile("dev")
    ResponseEntity<String> myPageModifyTest(String companyId, CompanyMyPageDTO companyMyPageDTO) {
        return companyCommandService.companyInfoModify(companyId, companyMyPageDTO);
    }
}
