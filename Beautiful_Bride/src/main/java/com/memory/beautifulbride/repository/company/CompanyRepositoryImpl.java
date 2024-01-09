package com.memory.beautifulbride.repository.company;

import com.memory.beautifulbride.dtos.company.CompanyMainPage;
import com.memory.beautifulbride.entitys.company.Company;
import com.memory.beautifulbride.entitys.company.QCompany;
import com.memory.beautifulbride.entitys.dress.QDressImagePath;
import com.memory.beautifulbride.entitys.dress.QDressInfo;
import com.memory.beautifulbride.entitys.dress.QDressMarkCount;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class CompanyRepositoryImpl implements CompanyRepositoryDsl {
    private final JPAQueryFactory jpaQueryFactory;
    private final QCompany qCompany = QCompany.company;
    private final QDressInfo qDressInfo = QDressInfo.dressInfo;
    private final QDressImagePath qDressImagePath = QDressImagePath.dressImagePath;
    private final QDressMarkCount qDressMarkCount = QDressMarkCount.dressMarkCount;

    @Override
    public void updateByCompanyInfo(Company company) {
        //    @Query("update Company c " +
//            "set c.address=:address, " +
//            "c.companyName=:companyName, " +
//            "c.companyPhone=:companyPhone " +
//            "where c.companyNo=:companyNo")
    }

    @Override
    public CompanyMainPage.thisCompanyDress findThisCompanyDress(Company company) {
        return thisCompanyDressProjections(company)
                .orderBy(qDressInfo.dressInfoIndex.desc())
                .fetchFirst();
    }

    @Override
    public List<CompanyMainPage.thisCompanyDress> findThisCompanyTop5DressList(Company company) {
        return thisCompanyDressProjections(company)
                .orderBy(qDressMarkCount.markCount.desc())
                .limit(5)
                .fetch();
    }

    @Override
    public List<CompanyMainPage.thisCompanyDress> findThisCompanyAllDressList(Company company) {
        return thisCompanyDressProjections(company)
                .orderBy(qDressInfo.dressInfoIndex.desc())
                .fetch();
    }

    private JPAQuery<CompanyMainPage.thisCompanyDress> thisCompanyDressProjections(Company company) {
        BooleanExpression booleanExpression = qDressInfo.company.eq(company)
                .and(qDressImagePath.path.like("%/front.%"));

        return jpaQueryFactory.select(
                        Projections.fields(
                                CompanyMainPage.thisCompanyDress.class,
                                qDressImagePath.path.as("dressImagePath"),
                                qDressInfo.dressName.as("dressName"),
                                qDressInfo.dressInfoIndex.as("dressInfoIndex"),
                                qDressMarkCount.markCount.as("markCount")
                        )
                )
                .from(qDressInfo)
                .leftJoin(qDressImagePath)
                .on(qDressInfo.dressInfoIndex.eq(qDressImagePath.dressInfo.dressInfoIndex))
                .leftJoin(qDressMarkCount)
                .on(qDressInfo.dressMarkCount.markIndex.eq(qDressMarkCount.markIndex))
                .distinct()
                .where(booleanExpression);
    }
}
