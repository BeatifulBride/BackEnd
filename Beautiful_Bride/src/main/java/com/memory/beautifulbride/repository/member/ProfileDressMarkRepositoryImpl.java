package com.memory.beautifulbride.repository.member;

import com.memory.beautifulbride.entitys.dress.DressInfo;
import com.memory.beautifulbride.entitys.dress.QDressInfo;
import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.entitys.member.*;
import com.memory.beautifulbride.repository.dress.DressInfoRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class ProfileDressMarkRepositoryImpl implements ProfileDressMarkRepositoryDsl {
    private final JPAQueryFactory jpaQueryFactory;
    private final QProfileDressMark qProfileDressMark = QProfileDressMark.profileDressMark;
    private final QMember qMember = QMember.member;


    @Override
    public ResponseEntity<String> dressMark(LoginData loginData, int dressInfoIndex) {
        return null;
    }

    @Override
    public boolean alreadyMark(DressInfo dressInfo, Profile profile) {
        return jpaQueryFactory.selectFrom(qProfileDressMark)
                .where(
                        qProfileDressMark.profile.eq(profile)
                                .and(qProfileDressMark.dressInfo.eq(dressInfo))
                )
                .fetchFirst() != null;
    }

    @Override
    public boolean notLimitMark(LoginData loginData) {
        int limitCount = switch (loginData.getKinds().getBasicsKinds()) {
            case FREE -> 5;
            case CHARGED -> 15;
            case COMPANY, ADMIN -> throw new IllegalArgumentException("즐겨찾기 가능한 권한이 아닙니다.");
        };

        JPAQuery<Integer> subQuery = jpaQueryFactory.select(qMember.profile.profileIndex)
                .from(qMember)
                .where(qMember.loginData.loginId.eq(loginData.getLoginId()));

        return Boolean.TRUE.equals(jpaQueryFactory
                .select(qProfileDressMark.count().loe(limitCount))
                .from(qProfileDressMark)
                .where(qProfileDressMark.profile.profileIndex.eq(subQuery))
                .fetchOne());
    }
}
