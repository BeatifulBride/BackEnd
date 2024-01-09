package com.memory.beautifulbride.service.member;

import com.memory.beautifulbride.dtos.member.MemberMyMarkDTO;
import com.memory.beautifulbride.dtos.profile.ProfileMainInfoDTO;
import com.memory.beautifulbride.dtos.profile.ProfileMemberDTO;
import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.repository.member.MemberRepository;
import com.memory.beautifulbride.repository.member.ProfileDressMarkRepository;
import com.memory.beautifulbride.repository.member.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log4j2
public class MemberReadOnlyService {

    private final ProfileRepository profileRepository;
    private final ProfileDressMarkRepository profileDressMarkRepository;

    public ProfileMainInfoDTO getMemberNameAndWeddingDay(String loginId) {
        return profileRepository
                .getProfileMainInfo(loginId)
                .orElseThrow(() -> entityNotFound(loginId));
    }

    public ProfileMemberDTO getMemberMyPageInfo(UserDetails userDetails) {
        isMemberAccessDeniedAction(userDetails);

        return profileRepository
                .getProfileMyPageInfo(userDetails.getUsername())
                .orElseThrow(() -> entityNotFound(userDetails.getUsername()));
    }

    protected ResponseStatusException entityNotFound(String loginId) {
        String errorMessage = "프로필을 찾지 못했습니다.";
        log.error("{} 요청 값 :: {}", errorMessage, loginId);
        return new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
    }

    public ResponseEntity<List<Integer>> myMarkIndexList(UserDetails userDetails) {
        isMember(userDetails);

        try {
            return ResponseEntity.ok(profileDressMarkRepository.findMyMarkList(userDetails.getUsername()));
        } catch (EntityNotFoundException e) {
            log.error(e);
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<MemberMyMarkDTO>> getMyPageMyMarkList(UserDetails userDetails) {
        if (isMember(userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            List<Integer> markIndexList = profileDressMarkRepository.findMyMarkList(userDetails.getUsername());
            return ResponseEntity.ok(profileDressMarkRepository.findMyMarkListDTO(userDetails, markIndexList));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(List.of(MemberMyMarkDTO.builder()
                    .dressCompanyName("잘못된 요청 입니다.")
                    .dressImagePath("잘못된 요청 입니다.")
                    .dressCompanyAddress("잘못된 요청 입니다.")
                    .build())
            );
        }
    }

    private boolean isMember(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .noneMatch(x -> LoginData.isNormalMember(x.getAuthority()));
    }

    private void isMemberAccessDeniedAction(UserDetails userDetails) {
        try {
            if (!isMember(userDetails)) {
                throw new AccessDeniedException("");
            }
        } catch (AccessDeniedException a) {
            log.error("일반 유저가 아닙니다 {}", a.getMessage());
        }
    }
}

