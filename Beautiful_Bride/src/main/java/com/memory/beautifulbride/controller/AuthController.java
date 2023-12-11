package com.memory.beautifulbride.controller;

import com.memory.beautifulbride.dtos.LoginDataRestPwdDto;
import com.memory.beautifulbride.dtos.CompanySignupDTO;
import com.memory.beautifulbride.dtos.MemberSignupDTO;
import com.memory.beautifulbride.service.logindata.LoginDataCommanService;
import com.memory.beautifulbride.service.logindata.LoginDataReadOnlyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final LoginDataReadOnlyService loginDataReadOnlyService;
    private final LoginDataCommanService loginDataCommanService;

    /**회원가입 단계에서 이미 아이디가 존재하는지 체크하는 메서드 입니다.*/
    @GetMapping("/auth/idcheck/{loginId}")
    ResponseEntity<String> accountIdCheck(@PathVariable String loginId) {
        return loginDataReadOnlyService.checkIfAccountIdExists(loginId);
    }

    /**
     * 이메일 체크와 아이디 체크를 Front에서 체크하고 넘어간다는 가정하에 요청됩니다.
     * 멤버 회원가입 요청입니다.
     */
    @PostMapping("/auth/signup/member")
    ResponseEntity<String> memberSignup(MemberSignupDTO signup) {
        return loginDataCommanService.createMemberAccount(signup);
    }


    /**
     * 이메일 체크와 아이디 체크를 Front에서 체크하고 넘어간다는 가정하에 요청됩니다.
     * 업체 회원가입 요청입니다.
     */
    @PostMapping("/auth/signup/company")
    ResponseEntity<String> companySignup(CompanySignupDTO signup) {
        return loginDataCommanService.createCompanyAccount(signup);
    }

    /**아이디 찾기 요청 입니다.*/
    @PostMapping("/find/account/id")
    String findAccountId(String loginEmail) {
        return loginDataReadOnlyService.getAccountId(loginEmail);
    }

    @PostMapping("/find/account/pwd")
    String resetAccountPwd(LoginDataRestPwdDto dto) {
        return null;
    }
}
