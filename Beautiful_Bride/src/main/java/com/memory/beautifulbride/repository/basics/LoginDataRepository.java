package com.memory.beautifulbride.repository.basics;

import com.memory.beautifulbride.entitys.basics.LoginData;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoginDataRepository extends JpaRepository<LoginData, Integer>, LoginDataRepositoryDsl {
    @EntityGraph(attributePaths = "KINDS")
    Optional<LoginData> findByLoginId(String id);

    @Query("select ld " +
            "from LoginData ld " +
            "where ld.loginEmail =:email")
    String findByLoginEmail(String email);

    @Query("select ld " +
            "from LoginData ld " +
            "where ld.loginId =: loginId and ld.loginEmail =: loginEmail")
    LoginData findByLoginData(String loginId, String loginEmail);

    @Modifying(flushAutomatically = true)
    @Query("update LoginData as ld " +
            "set ld.loginPwd =: resetPwd " +
            "where ld.loginId =: loginId and ld.loginEmail =: loginEmail"
    )
    void resetByLoginPwd(String loginId, String loginEmail, String resetPwd);
}
