package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.dtos.dress.DressListPageDTO;
import com.memory.beautifulbride.dtos.tryon.TryOnDTO;
import com.memory.beautifulbride.entitys.company.QCompany;
import com.memory.beautifulbride.entitys.dress.QDressImagePath;
import com.memory.beautifulbride.entitys.dress.QDressInfo;
import com.memory.beautifulbride.entitys.dress.QDressMarkCount;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Log4j2
public class DressInfoRepositoryImpl implements DressInfoRepositoryDsl {

    private final JPAQueryFactory jpaQueryFactory;

    private final QDressInfo qDressInfo = QDressInfo.dressInfo;

    private final QDressImagePath qDressImagePath = QDressImagePath.dressImagePath;
    private final QDressMarkCount qDressMarkCount = QDressMarkCount.dressMarkCount;
    private final QCompany qCompany = QCompany.company;


    public List<DressListPageDTO> getAllDresses() {
        return jpaQueryFactory
                .select(Projections.fields(
                        DressListPageDTO.class,
                        qDressInfo.dressInfoIndex.as("dressIndex"),
                        qDressInfo.dressName.as("dressName"),
                        qDressImagePath.path.as("dressImagePath"),
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
                .where(qDressInfo.company.companyName.eq(companyName))
                .orderBy(qDressMarkCount.markCount.desc())
                .limit(5)
                .fetch();

        return jpaQueryFactory
                .select(Projections.fields(
                        DressListPageDTO.class,
                        qDressInfo.dressInfoIndex.as("dressIndex"),
                        qDressInfo.dressName.as("dressName"),
                        qDressImagePath.path.as("dressImagePath"),
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
        QBean<DressListPageDTO> qBean = Projections.fields(
                DressListPageDTO.class,
                qDressInfo.dressInfoIndex.as("dressIndex"),
                qDressInfo.dressName.as("dressName"),
                qDressInfo.company.companyName.as("companyName")
        );

        List<DressListPageDTO> dressListPageDTOList = jpaQueryFactory
                .select(qBean)
                .from(qDressInfo)
                .orderBy(qDressMarkCount.markCount.desc()).limit(5)
                .fetch();

        List<Integer> indexList = dressListPageDTOList.stream().map(DressListPageDTO::getDressIndex).toList();

        List<String> pathList = jpaQueryFactory.select(qDressImagePath.path)
                .from(qDressImagePath)
                .where(qDressImagePath.dressInfo.dressInfoIndex.in(indexList)
                        .and(qDressImagePath.path.like("%/front.%"))
                )
                .fetch();

        return IntStream.range(0, Math.min(dressListPageDTOList.size(), pathList.size()))
                .mapToObj(index -> dressListPageDTOList.get(index).toBuilder()
                        .dressImagePath(pathList.get(index)).build()
                )
                .collect(Collectors.toList());
    }


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
        return dressListPageDTO.getDressImagePath();

    }


    /** {@inheritDoc} */
    @Override
    public void updateDressMarkCountTest(int dressInfoIndex, long count) {
        jpaQueryFactory.update(qDressMarkCount)
                .set(qDressMarkCount.markCount, count)
                .where(qDressMarkCount.markIndex
                        .in(jpaQueryFactory.select(qDressInfo.dressMarkCount.markIndex)
                                .from(qDressInfo).where(qDressInfo.dressInfoIndex.eq(dressInfoIndex))
                        )
                )
                .execute();
    }
}
