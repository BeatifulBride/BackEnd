package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.dtos.dress.DressListPageDTO;
import com.memory.beautifulbride.dtos.tryon.TryOnDTO;
import com.memory.beautifulbride.entitys.company.QCompany;
import com.memory.beautifulbride.entitys.dress.*;
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
public class DressInfoRepositoryImpl implements DressInfoRepositoryDsl {

    private final JPAQueryFactory jpaQueryFactory;
    private JPAQuery<DressInfo> profileJPAQuery;
    private final BooleanExpression booleanExpression = null;
    private PasswordEncoder passwordEncoder;
    private ProfileRepository profileRepository;

    private final QDressInfo qDressInfo = QDressInfo.dressInfo;

    private final QDressDetailsInfo qDressDetailsInfo = QDressDetailsInfo.dressDetailsInfo;
    private final QDressImagePath qDressImagePath = QDressImagePath.dressImagePath;
    private final QDressMarkCount qDressMarkCount = QDressMarkCount.dressMarkCount;
    private final QCompany qCompany =QCompany.company;

//    @Override
//    public ResponseEntity<String> dressNewRegistration(DressNewRegistrationDTO dressNewRegistrationDTO) {
//        return null;
//    }

    public List<DressListPageDTO> getAllDresses(){
        return jpaQueryFactory
                .select(Projections.fields(
                        DressListPageDTO.class,
                        qDressInfo.dressIndex,
                        qDressInfo.dressName,
                        qDressDetailsInfo.dressLineEnum, // Enum 값을 String으로 변환
                        qDressImagePath.path.as("dressPath"),
                        qCompany.companyName
                ))
                .from(qDressInfo)
                .leftJoin(qDressInfo.dressImagePath, qDressImagePath)
                .leftJoin(qDressInfo.dressDetailsInfo, qDressDetailsInfo)
                .leftJoin(qDressInfo.company, qCompany)
                .fetch();
    }

    @Override
    public List<DressListPageDTO> getcommpanyTop5Dresses(String companyName) {
        BooleanExpression companyFilter = companyName != null ? qDressInfo.company.companyName.eq(companyName) : null;

        return jpaQueryFactory
                .select(Projections.fields(
                        DressListPageDTO.class,
                        qDressInfo.dressIndex,
                        qDressInfo.dressName,
                        qDressDetailsInfo.dressLineEnum, // Enum 값을 String으로 변환
                        qDressImagePath.path.as("dressPath"),
                        qCompany.companyName
                ))
                .from(qDressInfo)
                .leftJoin(qDressInfo.dressImagePath, qDressImagePath)
                .leftJoin(qDressInfo.dressDetailsInfo, qDressDetailsInfo)
                .leftJoin(qDressInfo.company, qCompany)
                .leftJoin(qDressInfo.dressMarkCount, qDressMarkCount)
                .where(companyFilter)
                .orderBy(qDressMarkCount.markCount.desc()) // 조회수를 기준으로 내림차순 정렬
                .limit(5) // 상위 5개 결과만 가져오기
                .fetch();
    }

    @Override
    public List<DressListPageDTO> getTop5Dresses() {
        return jpaQueryFactory
                .select(Projections.fields(
                        DressListPageDTO.class,
                        qDressInfo.dressIndex,
                        qDressInfo.dressName,
                        qDressDetailsInfo.dressLineEnum, // Enum 값을 String으로 변환
                        qDressImagePath.path.as("dressPath"),
                        qCompany.companyName
                ))
                .from(qDressInfo)
                .leftJoin(qDressInfo.dressImagePath, qDressImagePath)
                .leftJoin(qDressInfo.dressDetailsInfo, qDressDetailsInfo)
                .leftJoin(qDressInfo.company, qCompany)
                .leftJoin(qDressInfo.dressMarkCount, qDressMarkCount)
                .orderBy(qDressMarkCount.markCount.desc()) // 조회수를 기준으로 내림차순 정렬
                .limit(5) // 상위 5개 결과만 가져오기
                .fetch();
    }


    @Override
    public String findByCompanyAndDressIndex(TryOnDTO tryOnDTO) {
        int dressIndex = tryOnDTO.getDressIndex();
        String companyName = tryOnDTO.getCommpanyName();


        DressListPageDTO dressListPageDTO = jpaQueryFactory
                .select(Projections.fields(
                        DressListPageDTO.class,
                        qDressInfo.dressIndex,
                        qDressInfo.dressName,
                        qDressInfo.company,
                        qDressInfo.dressImagePath
                ))
                .from(qDressInfo)
                .where(qDressInfo.dressIndex.eq(dressIndex)
                        .and(qDressInfo.company.companyName.eq(companyName)))
                .fetchOne();
        return dressListPageDTO.getDressPath();

    }






}
