package com.memory.beautifulbride.service.company;

import com.memory.beautifulbride.repository.company.DressImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyReadOnlyService {

    private final DressImageRepository dressImageRepository;
}