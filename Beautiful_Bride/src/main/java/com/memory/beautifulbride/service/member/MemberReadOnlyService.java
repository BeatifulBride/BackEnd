package com.memory.beautifulbride.service.member;

import com.memory.beautifulbride.dtos.profile.ProfileMainInfoDTO;
import com.memory.beautifulbride.dtos.profile.ProfileMyPageInfoDTO;
import com.memory.beautifulbride.repository.member.MemberRepository;
import com.memory.beautifulbride.repository.member.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class MemberReadOnlyService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public ProfileMainInfoDTO getMemberNameAndWeddingDay(String loginId) {
        return profileRepository
                .getProfileMainInfo(loginId)
                .orElseThrow(() -> entityNotFound(loginId));
    }

    public ProfileMyPageInfoDTO getMemberMyPageInfo(String loginId) {
        return profileRepository
                .getProfileMyPageInfo(loginId)
                .orElseThrow(() -> entityNotFound(loginId));
    }

    protected ResponseStatusException entityNotFound(String loginId) {
        String errorMessage = "프로필을 찾지 못했습니다.";
        log.error("{} 요청 값 :: {}", errorMessage, loginId);
        return new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
    }
}

