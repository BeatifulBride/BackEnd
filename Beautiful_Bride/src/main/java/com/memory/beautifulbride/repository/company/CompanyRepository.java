package com.memory.beautifulbride.repository.company;

import com.memory.beautifulbride.entitys.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer>, CompanyRepositoryDsl {
}
