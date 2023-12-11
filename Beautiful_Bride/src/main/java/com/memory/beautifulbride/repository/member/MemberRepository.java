package com.memory.beautifulbride.repository.member;

import com.memory.beautifulbride.entitys.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer>, MemberRepositoryDsl {
}
