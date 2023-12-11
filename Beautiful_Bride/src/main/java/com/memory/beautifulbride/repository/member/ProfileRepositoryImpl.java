package com.memory.beautifulbride.repository.member;

import com.memory.beautifulbride.dtos.profile.ProfileMainInfoDTO;
import com.memory.beautifulbride.dtos.profile.ProfileMyPageInfoDTO;
import com.memory.beautifulbride.dtos.profile.ProfileMyPageInfoDTO.DressMarkDataDTO;
import com.memory.beautifulbride.dtos.profile.ProfileMyPageInfoDTO.TryOnDataDTO;
import com.memory.beautifulbride.entitys.dress.QDressImagePath;
import com.memory.beautifulbride.entitys.dress.QDressInfo;
import com.memory.beautifulbride.entitys.dress.QTryOnImage;
import com.memory.beautifulbride.entitys.member.Profile;
import com.memory.beautifulbride.entitys.member.QProfile;
import com.memory.beautifulbride.entitys.member.QProfileDressMark;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class ProfileRepositoryImpl implements ProfileRepositoryDsl {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private JPAQuery<Profile> profileJPAQuery = null;
    private BooleanExpression booleanExpression = null;

    private final QProfile qProfile = QProfile.profile;
    private final QTryOnImage qTryOnImage = QTryOnImage.tryOnImage;
    private final QProfileDressMark qProfileDressMark = QProfileDressMark.profileDressMark;
    private final QDressImagePath qDressImagePath = QDressImagePath.dressImagePath;
    private final QDressInfo qDressInfo = QDressInfo.dressInfo;


    @Override
    public Optional<ProfileMainInfoDTO> getProfileMainInfo(String loginId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(Projections.fields(
                                ProfileMainInfoDTO.class,
                                qProfile.memName,
                                qProfile.memWeddingDate
                        ))
                        .from(qProfile)
                        .where(getBxProfileMemIdEq(loginId))
                        .fetchOne());
    }

    @Override
    public Optional<ProfileMyPageInfoDTO> getProfileMyPageInfo(String loginId) {
        Tuple profileTuple = jpaQueryFactory
                .select(qProfile.memName,
                        qProfile.memWeddingDate,
                        qProfile.profileIndex)
                .from(qProfile)
                .where(getBxProfileMemIdEq(loginId))
                .fetchOne();

        if (profileTuple == null) throw new EntityNotFoundException("프로필을 찾을 수 없습니다.");

        Integer profileIndex = profileTuple.get(qProfile.profileIndex);
        String memName = profileTuple.get(qProfile.memName);
        Date weddingDate = profileTuple.get(qProfile.memWeddingDate);

        List<DressMarkDataDTO> markDataDTOList = getDressMarkDataDTOList(profileIndex);
        List<TryOnDataDTO> tryOnDataDTOList = getTryOnDataDTOList(profileIndex);

        return Optional.ofNullable(ProfileMyPageInfoDTO.builder()
                .memName(memName)
                .weddingDate(weddingDate)
                .tryOnImageDataList(tryOnDataDTOList)
                .dressMarkDataList(markDataDTOList)
                .build()
        );
    }

    protected BooleanExpression getBxProfileMemIdEq(String loginId) {
        return qProfile.member.loginData.loginId.eq(loginId);
    }

    private List<DressMarkDataDTO> getDressMarkDataDTOList(Integer profileIndex) {

        NumberPath<Integer> dressIndex;
        StringPath dressImagePath;
        StringPath dressPNumber;
        StringPath companyName;

        return null;
    }

    private List<TryOnDataDTO> getTryOnDataDTOList(Integer profileIndex) {

        StringPath tryOnImagePath = qTryOnImage.tryOnPath;
        StringPath tryOnDressPNumber = qTryOnImage.dressImage.dressPNumber;
        StringPath tryOnCompanyName = qTryOnImage.dressImage.company.companyName;

        return jpaQueryFactory
                .select(tryOnImagePath, tryOnDressPNumber, tryOnCompanyName)
                .from(qTryOnImage)
                .where(qTryOnImage.profile.profileIndex.eq(profileIndex))
                .fetch()
                .stream().map(tuple -> TryOnDataDTO.builder()
                        .tryOnPath(tuple.get(tryOnImagePath))
                        .dressPNumber(tuple.get(tryOnDressPNumber))
                        .companyName(tuple.get(tryOnCompanyName))
                        .build()).toList();
    }
}
