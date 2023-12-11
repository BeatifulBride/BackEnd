package com.memory.beautifulbride.repository.member;

import com.memory.beautifulbride.entitys.member.Profile;
import com.memory.beautifulbride.entitys.member.QProfile;
import com.memory.beautifulbride.dtos.ProfileMainInfoDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;

@RequiredArgsConstructor
@Log4j2
public class ProfileRepositoryImpl implements ProfileRepositoryDsl {

    private final JPAQueryFactory jpaQueryFactory;
    private JPAQuery<Profile> profileJPAQuery;
    private BooleanExpression booleanExpression = null;
    private PasswordEncoder passwordEncoder;
    private ProfileRepository profileRepository;
    private QProfile qProfile = QProfile.profile;

    @Override
    public ProfileMainInfoDTO getMemMainInfo(Principal principal) {

        booleanExpression = qProfile.member.loginData.loginId.eq(principal.getName());

        profileJPAQuery.select(
                        Projections.fields(Profile.class, qProfile.memName, qProfile.memWeddingDate)
                )
                .from(qProfile.member.loginData)
                .where(booleanExpression)
                .fetchOne();

        return null;
    }
}
