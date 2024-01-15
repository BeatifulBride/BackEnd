package com.memory.beautifulbride.controller;

import com.memory.beautifulbride.dtos.company.CompanyInfoModifyDTO;
import com.memory.beautifulbride.dtos.company.CompanyMainPage;
import com.memory.beautifulbride.service.company.CompanyCommandService;
import com.memory.beautifulbride.service.company.CompanyReadOnlyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/com")
public class CompanyController {
    private final CompanyReadOnlyService companyReadOnlyService;
    private final CompanyCommandService companyCommandService;

    @PostMapping(value = "/mypage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "업체 마이 페이지 정보 입니다.",
            security = @SecurityRequirement(name = "Authorization"))
    ResponseEntity<CompanyMainPage> myPage(@Parameter(hidden = true) UserDetails userDetails) {
        return companyReadOnlyService.getCompanyMainPageInfo(userDetails.getUsername());
    }

    @PutMapping("/mypage/modify")
    @Operation(
            summary = "업체 마이 페이지 정보 수정 입니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    ResponseEntity<String> myPageModify(@Parameter(hidden = true) UserDetails userDetails, @Validated @RequestBody CompanyInfoModifyDTO dto) {
        return companyCommandService.modifyCompanyInfo(userDetails, dto);
    }
}
