package com.memory.beautifulbride.repository.member;

import com.memory.beautifulbride.entitys.dress.DressInfo;
import com.memory.beautifulbride.entitys.member.Profile;
import com.memory.beautifulbride.entitys.member.ProfileDressMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileDressMarkRepository extends JpaRepository<ProfileDressMark, Long>, ProfileDressMarkRepositoryDsl {
    @Query("delete ProfileDressMark where dressInfo=:dressInfo and profile=:profile")
    @Modifying
    void markRemove(@Param("dressInfo") DressInfo dressInfo, @Param("profile") Profile profile);

    @Query("select m.dressInfo.dressInfoIndex from ProfileDressMark m where m.profile.member.loginData.loginId=:memberId")
    List<Integer> findMyMarkList(@Param("memberId") String memberId);
}

