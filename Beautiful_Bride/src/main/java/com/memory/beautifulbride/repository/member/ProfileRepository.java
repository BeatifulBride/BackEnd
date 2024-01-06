package com.memory.beautifulbride.repository.member;

import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.entitys.member.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ProfileRepository extends JpaRepository<Profile, Integer>, ProfileRepositoryDsl {
    @Query("select p from Profile p where p.member.loginData=:loginData")
    Optional<Profile> findProfileByLoginData(@Param("loginData") LoginData loginData);
}
