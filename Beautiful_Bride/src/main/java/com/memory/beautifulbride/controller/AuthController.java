package com.memory.beautifulbride.controller;

import com.memory.beautifulbride.config.jwt.JwtFilter;
import com.memory.beautifulbride.config.jwt.TokenProvider;
import com.memory.beautifulbride.dtos.company.CompanySignupDTO;
import com.memory.beautifulbride.dtos.logindata.LoginDTO;
import com.memory.beautifulbride.dtos.logindata.LoginDataRestPwdDTO;
import com.memory.beautifulbride.dtos.member.MemberSignupDTO;
import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.service.logindata.LoginDataCommandService;
import com.memory.beautifulbride.service.logindata.LoginDataReadOnlyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
public class AuthController {
    private final LoginDataReadOnlyService loginDataReadOnlyService;
    private final LoginDataCommandService loginDataCommandService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping("/auth/idcheck/{loginId}")
    @Operation(
            summary = "회원가입-기존 아이디 여부 확인",
            description = "회원가입시 사용됩니다.")
    ResponseEntity<String> accountIdCheck(@PathVariable(name = "loginId") String loginId) {
        return loginDataReadOnlyService.checkIfAccountIdExists(loginId);
    }

    @GetMapping("/auth/emailcheck/{loginEmail}")
    @Operation(
            summary = "회원가입-기존 이메일 여부 확인",
            description = "회원가입시 사용됩니다.")
    ResponseEntity<String> accountEmailCheck(@PathVariable(name = "loginEmail") String loginEmail) {
        return loginDataReadOnlyService.checkIfAccountEmailExists(loginEmail);
    }

    @PostMapping("/auth/signup/member")
    @Operation(
            summary = "'멤버'회원 가입 요청입니다.",
            description = "이메일 체크와 아이디 체크를 Front에서 확인하고 넘어간다는 가정하에 요청됩니다.")
    ResponseEntity<String> memberSignup(@Validated @RequestBody MemberSignupDTO signup) {
        return loginDataCommandService.createMemberAccount(signup);
    }


    @PostMapping("/auth/signup/company")
    @Operation(
            summary = "'업체'회원 가입 요청입니다.",
            description = "이메일 체크와 아이디 체크를 Front에서 체크하고 넘어간다는 가정하에 요청됩니다.")
    ResponseEntity<String> companySignup(@Validated @ModelAttribute CompanySignupDTO signup) {
        return loginDataCommandService.createCompanyAccount(signup);
    }

    @PostMapping("/find/account/id")
    @Operation(summary = "아이디 찾기 요청입니다.")
    ResponseEntity<String> findAccountId(@RequestParam(name = "loginEmail") @NotBlank String loginEmail) {
        return ResponseEntity.ok(loginDataReadOnlyService.getAccountId(loginEmail));
    }

    @PostMapping("/find/account/pwd")
    @Operation(summary = "비밀번호 재설정 요청입니다.")
    ResponseEntity<String> resetAccountPwd(@Validated @ModelAttribute LoginDataRestPwdDTO dto) {
        return loginDataCommandService.resetAccountPwd(dto);
    }

    @GetMapping("find/account/check/{loginId}/{loginEmail}")
    @Operation(summary = "사용자가 계정을 찾기 위한 요청입니다")
    ResponseEntity<String> accountCheck(@PathVariable(name = "loginId") @NotBlank String loginId,
                                        @PathVariable(name = "loginEmail") @NotBlank String loginEmail) {
        return loginDataReadOnlyService.checkAccountIdandEmail(loginId, loginEmail);
    }

    @PostMapping(value = "/auth/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Operation(summary = "로그인 요청 입니다.")
    ResponseEntity<Map<String, String>> login(@Validated @ModelAttribute LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.LOGIN_ID(), loginDTO.LOGIN_PWD());

        Authentication authentication;
        try {
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("fail", "아이디 혹은 비밀번호가 틀렸습니다."));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.authorizationHeader, "Bearer " + accessToken);

        boolean isCompany = LoginData.isCompanyMember(authentication.getAuthorities());
        Map<String, String> accountMapData = Map.of(
                "token", accessToken,
                "company", String.valueOf(isCompany)
        );

        return new ResponseEntity<>(accountMapData, httpHeaders, HttpStatus.OK);
    }
}
