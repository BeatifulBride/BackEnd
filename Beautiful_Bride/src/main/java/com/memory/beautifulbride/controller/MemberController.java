package com.memory.beautifulbride.controller;

import com.memory.beautifulbride.dtos.member.MemberMyMarkDTO;
import com.memory.beautifulbride.dtos.profile.ProfileMainInfoDTO;
import com.memory.beautifulbride.service.member.MemberCommandService;
import com.memory.beautifulbride.service.member.MemberReadOnlyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mem")
public class MemberController {
    private final MemberCommandService memberCommandService;
    private final MemberReadOnlyService memberReadOnlyService;

    @GetMapping(value = "/maininfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "멤버 이름과 웨딩 날짜를 반환합니다.",
            description = "메인 페이지에서 사용되는 멤버 정보입니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    ResponseEntity<ProfileMainInfoDTO> getMemberMainInfo(@Parameter(hidden = true) UserDetails userDetails) {
        ProfileMainInfoDTO dto = memberReadOnlyService.getMemberNameAndWeddingDay(userDetails.getUsername());
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/mark/{dressIndex}")
    @Operation(
            summary = "드레스 즐겨찾기를 추가 합니다. 실제로 사용되는 요청입니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    ResponseEntity<String> dressMark(
            @Parameter(hidden = true) UserDetails userDetails,
            @PathVariable @NotNull int dressIndex
    ) {
        return memberCommandService.memberMark(userDetails.getUsername(), dressIndex);
    }

    @GetMapping(value = "/mark/mymark")
    @Operation(
            summary = "일반 유저가 즐겨찾기한 드레스의 인덱스 리스트를 반환합니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    ResponseEntity<List<Integer>> dressMyMarkList(@Parameter(hidden = true) UserDetails userDetails) {
        return memberReadOnlyService.myMarkIndexList(userDetails);
    }

    @GetMapping(value = "/mypage/mymark/list")
    @Operation(
            summary = "마이페이지 - 일반 유저가 즐겨찾기한 드레스의 리스트를 반환합니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    ResponseEntity<List<MemberMyMarkDTO>> myPageMyMarkList(@Parameter(hidden = true) UserDetails userDetails) {
        return memberReadOnlyService.getMyPageMyMarkList(userDetails);
    }
}
