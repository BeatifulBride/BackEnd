package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.dtos.dress.DressMyPageViewDTO;
import com.memory.beautifulbride.entitys.dress.DressInfo;
import com.memory.beautifulbride.entitys.dress.QDressInfo;
import com.memory.beautifulbride.repository.member.ProfileRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class DressRepositoryImpl implements DressRepositoryDsl {

    private final JPAQueryFactory jpaQueryFactory;
    private JPAQuery<DressInfo> profileJPAQuery;
    private final BooleanExpression booleanExpression = null;
    private PasswordEncoder passwordEncoder;
    private ProfileRepository profileRepository;

    private final QDressInfo qDressInfo = QDressInfo.dressInfo;

    @Override
    public List<DressMyPageViewDTO> allDressList() {
        jpaQueryFactory.select(Projections.fields(
                DressMyPageViewDTO.class,
                qDressInfo.dressName,
                qDressInfo.dressDetailsInfo.dressLineEnum,
                qDressInfo.company.companyName
        )).from(qDressInfo).fetch();
        return null;
    }
}
