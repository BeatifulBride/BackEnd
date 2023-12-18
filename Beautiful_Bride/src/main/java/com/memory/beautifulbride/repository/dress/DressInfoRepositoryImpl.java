package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.dtos.dress.DressNewRegistrationDTO;
import com.memory.beautifulbride.entitys.dress.DressInfo;
import com.memory.beautifulbride.entitys.dress.QDressInfo;
import com.memory.beautifulbride.repository.member.ProfileRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Log4j2
public class DressInfoRepositoryImpl implements DressInfoRepositoryDsl {

    private final JPAQueryFactory jpaQueryFactory;
    private JPAQuery<DressInfo> profileJPAQuery;
    private final BooleanExpression booleanExpression = null;
    private PasswordEncoder passwordEncoder;
    private ProfileRepository profileRepository;

    private final QDressInfo qDressInfo = QDressInfo.dressInfo;

    /**{@inheritDoc}*/
    @Override
    public ResponseEntity<String> dressNewRegistration(DressNewRegistrationDTO dressNewRegistrationDTO) {
        return null;
    }
}
