package com.memory.beautifulbride.repository.member;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Log4j2
public class MemberRepositoryImpl implements MemberRepositoryDsl {

    private final JPAQueryFactory jpaQueryFactory;
    private BooleanExpression booleanExpression = null;
    private PasswordEncoder passwordEncoder;
    private MemberRepository memberRepository;

}
