package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.dtos.dress.DressListPageDTO;
import com.memory.beautifulbride.dtos.tryon.TryOnDTO;
import com.memory.beautifulbride.entitys.company.QCompany;
import com.memory.beautifulbride.entitys.dress.DressInfo;
import com.memory.beautifulbride.entitys.dress.QDressImagePath;
import com.memory.beautifulbride.entitys.dress.QDressInfo;
import com.memory.beautifulbride.entitys.dress.QDressMarkCount;
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

    private final QDressImagePath qDressImagePath = QDressImagePath.dressImagePath;
    private final QDressMarkCount qDressMarkCount = QDressMarkCount.dressMarkCount;
    private final QCompany qCompany = QCompany.company;


    //수정이 필요함
    @Override
    public List<DressListPageDTO> getAllDresses() {
        return jpaQueryFactory
                .select(Projections.fields(
                        DressListPageDTO.class,
                        qDressInfo.dressInfoIndex.as("dressIndex"),
                        qDressInfo.dressName.as("dressName"),
                        qDressImagePath.path.as("dressPath"),
                        qCompany.companyName.as("companyName")
                ))
                .from(qDressInfo)
                .leftJoin(qDressInfo.dressImagePath, qDressImagePath).on(qDressImagePath.path.contains("front"))
                .leftJoin(qDressInfo.company, qCompany)
                .fetch();
    }

    @Override
    public List<DressListPageDTO> getCompanyTop5Dresses(String companyName) {
        List<Integer> companyFilteredSubQuery = jpaQueryFactory
                .select(qDressInfo.dressInfoIndex)
                .from(qDressInfo)
                .where(companyName != null ? qDressInfo.company.companyName.eq(companyName) : null)
                .orderBy(qDressMarkCount.markCount.desc())
                .limit(5)
                .fetch();

        //현재 사용중인 쿼리에서 조회가 잘 되고 있지 않다
        // Main query using the results of the subquery
        return jpaQueryFactory
                .select(Projections.fields(
                        DressListPageDTO.class,
                        qDressInfo.dressInfoIndex.as("dressIndex"),
                        qDressInfo.dressName.as("dressName"),
                        qDressImagePath.path.as("dressPath"),
                        qCompany.companyName.as("companyName")
                ))
                .from(qDressInfo)
                .leftJoin(qDressInfo.dressImagePath, qDressImagePath)
                .leftJoin(qDressInfo.company, qCompany)
                .where(qDressInfo.dressInfoIndex.in(companyFilteredSubQuery)
                        .and(qDressImagePath.path.contains("front")))
                .fetch();
    }
    @Override
    public List<DressListPageDTO> getTop5Dresses() {
        List<Integer> subQuery = jpaQueryFactory
                .select(qDressInfo.dressInfoIndex)
                .from(qDressInfo)
                .orderBy(qDressMarkCount.markCount.desc())
                .limit(5)
                .fetch();

        // Use the subquery result as a filter for the main query
        return jpaQueryFactory
                .select(Projections.fields(
                        DressListPageDTO.class,
                        qDressInfo.dressInfoIndex.as("dressIndex"),
                        qDressInfo.dressName.as("dressName"),
                        qDressImagePath.path.as("dressPath"),
                        qCompany.companyName.as("companyName")
                ))
                .from(qDressInfo)
                .leftJoin(qDressInfo.dressImagePath, qDressImagePath)
                .leftJoin(qDressInfo.company, qCompany)
                .where(qDressInfo.dressInfoIndex.in(subQuery)
                        .and(qDressImagePath.path.contains("front")))
                .fetch();
    }
//
//    @Override
//    public DressDetailsinfoViewDTO getDressAllCollomData(String dressIndex) {
//        // Use the subquery result as a filter for the main query
//        List<Integer> dressindexcolumfindsubquery= jpaQueryFactory
//                .select(qDressInfo.dressInfoIndex)
//                .from(qDressInfo)
//                .where(dressIndex != null ? qDressInfo.dressInfoIndex.eq(Integer.valueOf(dressIndex))  : null)
//                .fetch();
//
//        //현재 사용중인 쿼리에서 조회가 잘 되고 있지 않다
//        // Main query using the results of the subquery
//        return (DressDetailsinfoViewDTO) jpaQueryFactory
//                .select(Projections.fields(
//                        DressDetailsinfoViewDTO.class,
//                        qDressInfo.dressInfoIndex.as("dressIndex"),
//                        qDressInfo.dressName.as("dressName"),
//                        qDressInfo.dressPNumber.as("deressPNumber"),
//                        qDressInfo.dressPrice.as("dressPrice"),
//                        qDressImagePath.path.as("dressPath"),
//                        qCompany.companyName.as("companyName")
//
//                ))
//                .from(qDressInfo)
//                .leftJoin(qDressInfo.dressImagePath, qDressImagePath)
//                .leftJoin(qDressInfo.company, qCompany)
//                .where(qDressInfo.dressInfoIndex.in(dressindexcolumfindsubquery))
//                .fetch();
//    }


    @Override
    public String findByCompanyAndDressIndex(TryOnDTO tryOnDTO) {
        int dressIndex = tryOnDTO.getDressIndex();
        String companyName = tryOnDTO.getCommpanyName();


        DressListPageDTO dressListPageDTO = jpaQueryFactory
                .select(Projections.fields(
                        DressListPageDTO.class,
                        qDressInfo.dressInfoIndex,
                        qDressInfo.dressName,
                        qDressInfo.company,
                        qDressInfo.dressImagePath
                ))
                .from(qDressInfo)
                .where(qDressInfo.dressInfoIndex.eq(dressIndex)
                        .and(qDressInfo.company.companyName.eq(companyName)))
                .fetchOne();
        return dressListPageDTO.getDressPath();

    }


    @Override
    public void updateDressMarkCount(int dressInfoIndex, int count) {

    }
}
