package com.memory.beautifulbride.repository.basics;

import com.memory.beautifulbride.entitys.member.Profile;
import com.memory.beautifulbride.repository.member.ProfileRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Log4j2
public class LoginDataRepositoryImpl implements LoginDataRepositoryDsl {

    private final JPAQueryFactory jpaQueryFactory;
    private JPAQuery<Profile> profileJPAQuery;
    private BooleanExpression booleanExpression = null;
    private PasswordEncoder passwordEncoder;
    private ProfileRepository profileRepository;
}
