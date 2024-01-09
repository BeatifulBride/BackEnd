package com.memory.beautifulbride.repository.company;

import com.memory.beautifulbride.dtos.company.CompanyMainPage;
import com.memory.beautifulbride.entitys.company.Company;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface CompanyRepositoryDsl {
    @Modifying
    void updateByCompanyInfo(Company company);

    CompanyMainPage.thisCompanyDress findThisCompanyDress(Company company);

    List<CompanyMainPage.thisCompanyDress> findThisCompanyTop5DressList(Company company);

    List<CompanyMainPage.thisCompanyDress> findThisCompanyAllDressList(Company company);
}
