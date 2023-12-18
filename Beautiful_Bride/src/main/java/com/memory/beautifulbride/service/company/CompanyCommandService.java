package com.memory.beautifulbride.service.company;


import com.memory.beautifulbride.repository.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class CompanyCommandService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
}
