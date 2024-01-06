package com.memory.beautifulbride.controller;

import com.memory.beautifulbride.dtos.profile.ProfileMainInfoDTO;
import com.memory.beautifulbride.dtos.profile.ProfileMemberDTO;
import com.memory.beautifulbride.service.member.MemberCommandService;
import com.memory.beautifulbride.service.member.MemberReadOnlyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mem")
public class MemberController {
    private final MemberCommandService memberCommandService;
    private final MemberReadOnlyService memberReadOnlyService;


    //Get으로 변경
    @GetMapping(value = "/maininfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "멤버 이름과 웨딩 날짜를 반환합니다.",
            description = "메인 페이지에서 사용되는 멤버 정보입니다.")
    ResponseEntity<ProfileMainInfoDTO> getMemberMainInfo(Principal principal) {
        return ResponseEntity.ok(memberReadOnlyService.getMemberNameAndWeddingDay(principal.getName()));
    }

    @GetMapping(value = "/maininfoTest", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "멤버 이름과 웨딩 날짜를 반환합니다.",
            description = "메인 페이지에서 사용되는 멤버 정보입니다.")
    @Profile("dev")
    ResponseEntity<ProfileMainInfoDTO> getMemberMainInfoTest(
            @RequestParam(name = "loginId", defaultValue = "Member100") String loginId) {
        return ResponseEntity.ok(memberReadOnlyService.getMemberNameAndWeddingDay(loginId));
    }

    @PostMapping(value = "/mypage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "멤버의 프로필 정보를 반환합니다.",
            description = "프로필 페이지에서 사용되는 멤버의 정보입니다.")
    ResponseEntity<ProfileMemberDTO> getMemberMyPageInfo(Principal principal) {
        return ResponseEntity.ok(memberReadOnlyService.getMemberMyPageInfo(principal.getName()));
    }

    @PostMapping(value = "/mypageTest", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "멤버의 프로필 정보를 반환합니다.", description = "프로필 페이지에서 사용되는 멤버의 정보입니다.")
    @Profile("dev")
    ResponseEntity<ProfileMemberDTO> getMemberMyPageInfoTest(
            @RequestParam(name = "loginId", defaultValue = "Member100") String loginId) {
        return ResponseEntity.ok(memberReadOnlyService.getMemberMyPageInfo(loginId));
    }

    @GetMapping(value = "/mark{dressIndex}")
    ResponseEntity<String> dressMark(Principal principal, @PathVariable int dressIndex) {
        return memberCommandService.memberMark(principal.getName(), dressIndex);
    }

    @GetMapping(value = "/markTest{dressIndex}")
    @Operation(summary = "드레스 즐겨찾기를 추가 합니다.")
    @Profile("dev")
    ResponseEntity<String> dressMark(String memberLoginId, @PathVariable int dressIndex) {
        return memberCommandService.memberMark(memberLoginId, dressIndex);
    }
}
