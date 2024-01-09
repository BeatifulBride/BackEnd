package com.memory.beautifulbride.repository.company;

import com.memory.beautifulbride.entitys.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer>, CompanyRepositoryDsl {
    @Query("select c from Company c where c.loginData.loginId =:loginId")
    Optional<Company> searchFromLoginData(@Param("loginId") String loginId);

    @Query("select count(a) > 0 from Company a")
    boolean existsAny();

    @Query("select count (*) from DressInfo d where d.company=:company")
    int dressAllRegistrationCount(@Param("company") Company company);

    @Query("select count(a) > 1 from Company a where a.loginData.loginId=:companyId")
    boolean checkCompanyExists(@Param("companyId") String companyId);

    @Query("update Company c " +
            "set c.companyName=:companyName, c.address=:companyAddress, c.companyPhone=:companyPhone " +
            "where c.loginData.loginId=:loginId")
    @Modifying
    void modifyCompanyInfo(String loginId, String companyName, String companyAddress, String companyPhone);
}
