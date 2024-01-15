package com.memory.beautifulbride.repository.logindata;

import com.memory.beautifulbride.entitys.logindata.BasicsKinds;
import com.memory.beautifulbride.entitys.logindata.LoginData;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoginDataRepository extends JpaRepository<LoginData, Integer>, LoginDataRepositoryDsl {

    /** 아이디값으로 로그인 정보를 가져옮과 동시에 권한 까지 가져오기 위한 코드 */
    @EntityGraph(attributePaths = "kinds")
    Optional<LoginData> findByLoginId(String loginId);

    /** 오직 권한값 확인을 위한 쿼리 */
    @Query("select l.kinds.basicsKinds from LoginData l where l.loginId=:loginId")
    Optional<BasicsKinds> findBasicsKinds(@Param("loginId") String loginId);

    @Query("select count(ld) > 0 from LoginData ld where ld.loginId = :loginId")
    boolean existsByLoginId(@Param("loginId") String loginId);

    /** 이미 이메일 값이 존재하는지 확인하기 위한 코드 */
    @Query("select count(ld) > 0 from LoginData ld where ld.loginEmail =:loginEmail")
    boolean existsByLoginEmail(@Param("loginEmail") String loginEmail);

    /** 이메일로 아이디를 찾기 위한 코드 */
    @Query("select ld.loginId from LoginData ld where ld.loginEmail =:loginEmail")
    String findLoginIdByEmail(@Param("loginEmail") String loginEmail);

    /** 아이디와 이메일을 받아 로그인 정보를 찾기 위한 코드 */
    @Query("select ld from LoginData ld " +
            "where ld.loginId =:loginId and ld.loginEmail =:loginEmail")
    Optional<LoginData> findByLoginData(
            @Param("loginId") String loginId,
            @Param("loginEmail") String loginEmail);

    /** 비밀번호를 수정하기 위한 코드 */
    @Modifying
    @Query("update LoginData as ld " +
            "set ld.loginPwd =:resetPwd " +
            "where ld.loginId =:loginId and ld.loginEmail =:loginEmail")
    void resetByLoginPwd(@Param("loginId") String loginId,
                         @Param("loginEmail") String loginEmail,
                         @Param("resetPwd") String resetPwd);

    /** 테스트 용도입니다. 쓰지 마세요!!! */
    @Query("select l from LoginData l where l.kinds.basicsKinds='COMPANY'")
    List<LoginData> findByCompany();
}
