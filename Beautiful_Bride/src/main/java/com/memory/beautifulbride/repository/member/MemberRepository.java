package com.memory.beautifulbride.repository.member;

import com.memory.beautifulbride.entitys.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Integer>, MemberRepositoryDsl {
    @Query("select count(a) > 0 from Member a")
    boolean existsAny();
}
