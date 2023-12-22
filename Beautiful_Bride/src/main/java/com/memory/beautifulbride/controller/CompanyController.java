package com.memory.beautifulbride.controller;

import com.memory.beautifulbride.service.company.CompanyCommandService;
import com.memory.beautifulbride.service.company.CompanyReadOnlyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comapny")
public class CompanyController {
    private final CompanyReadOnlyService companyReadOnlyService;
    private final CompanyCommandService companyCommandService;


}
