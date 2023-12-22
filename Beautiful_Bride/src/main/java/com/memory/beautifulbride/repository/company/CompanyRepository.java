package com.memory.beautifulbride.repository.company;

import com.memory.beautifulbride.entitys.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, Integer>, CompanyRepositoryDsl {
    @Query("select c from Company c where c.loginData.loginId =:loginId")
    Company searchFromLoginData(@Param("loginId") String loginId);

    @Query("select count(a) > 0 from Company a")
    boolean existsAny();
}
