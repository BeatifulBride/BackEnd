package com.memory.beautifulbride.controller;

import com.memory.beautifulbride.dtos.profile.ProfileMainInfoDTO;
import com.memory.beautifulbride.dtos.profile.ProfileMyPageInfoDTO;
import com.memory.beautifulbride.service.member.MemberCommandService;
import com.memory.beautifulbride.service.member.MemberReadOnlyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mem")
public class MemberController {
    private final MemberCommandService memberCommandService;
    private final MemberReadOnlyService memberReadOnlyService;

    @PostMapping(value = "/maininfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "멤버 이름과 웨딩 날짜를 반환합니다.",
            description = "메인 페이지에서 사용되는 멤버 정보입니다.")
    ResponseEntity<ProfileMainInfoDTO> getMemberMainInfo(Principal principal) {
        return ResponseEntity.ok(memberReadOnlyService.getMemberNameAndWeddingDay(principal.getName()));
    }

    @PostMapping(value = "/maininfoTest", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "멤버 이름과 웨딩 날짜를 반환합니다.",
            description = "메인 페이지에서 사용되는 멤버 정보입니다.")
    ResponseEntity<ProfileMainInfoDTO> getMemberMainInfoTest(
            @RequestParam(name = "loginId", defaultValue = "Member100") String loginId) {
        return ResponseEntity.ok(memberReadOnlyService.getMemberNameAndWeddingDay(loginId));
    }

    @PostMapping(value = "/mypage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "멤버의 프로필 정보를 반환합니다.",
    description = "프로필 페이지에서 사용되는 멤버의 정보입니다.")
    ResponseEntity<ProfileMyPageInfoDTO> getMemberMyPageInfo(Principal principal) {
        return ResponseEntity.ok(memberReadOnlyService.getMemberMyPageInfo(principal.getName()));
    }

    @PostMapping(value = "/mypageTest", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "멤버의 프로필 정보를 반환합니다.",
            description = "프로필 페이지에서 사용되는 멤버의 정보입니다.")
    ResponseEntity<ProfileMyPageInfoDTO> getMemberMyPageInfoTest(
            @RequestParam(name = "loginId", defaultValue = "Member100") String loginId) {
        return ResponseEntity.ok(memberReadOnlyService.getMemberMyPageInfo(loginId));
    }
}
