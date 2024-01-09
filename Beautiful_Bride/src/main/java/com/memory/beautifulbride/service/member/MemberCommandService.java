package com.memory.beautifulbride.service.member;

import com.memory.beautifulbride.entitys.dress.DressInfo;
import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.entitys.member.Profile;
import com.memory.beautifulbride.entitys.member.ProfileDressMark;
import com.memory.beautifulbride.repository.dress.DressInfoRepository;
import com.memory.beautifulbride.repository.logindata.LoginDataRepository;
import com.memory.beautifulbride.repository.member.ProfileDressMarkRepository;
import com.memory.beautifulbride.repository.member.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberCommandService {

    private final LoginDataRepository loginDataRepository;
    private final ProfileRepository profileRepository;
    private final ProfileDressMarkRepository profileDressMarkRepository;
    private final DressInfoRepository dressInfoRepository;


    /** 일반 유저 좋아요 요청 */
    public ResponseEntity<String> memberMark(String memberLoginId, int dressIndex) {
        String responseMassage = "즐겨찾기에 추가 되었습니다.";
        LoginData loginData = loginDataRepository.findByLoginId(memberLoginId)
                .filter(LoginData::isNormalMember)
                .orElseThrow(() -> new RuntimeException("일반 유저 혹은 결제 유저가 아닙니다."));

        DressInfo dressInfo = dressInfoRepository.getDressInfo(dressIndex)
                .orElseThrow(() -> new EntityNotFoundException("드레스를 찾지 못했습니다."));

        Profile profile = profileRepository.findProfileByLoginData(loginData)
                .orElseThrow(() -> new EntityNotFoundException("프로필 데이터를 찾지 못했습니다."));

        if (!profileDressMarkRepository.alreadyMark(dressInfo, profile)) {
            ProfileDressMark profileDressMark = ProfileDressMark.builder()
                    .profile(profile)
                    .dressInfo(dressInfo)
                    .build();
            profileDressMarkRepository.save(profileDressMark);
        } else {
            profileDressMarkRepository.markRemove(dressInfo, profile);
            responseMassage = "즐겨찾기가 취소 되었습니다.";
        }

        return ResponseEntity.ok(responseMassage);
    }
}
