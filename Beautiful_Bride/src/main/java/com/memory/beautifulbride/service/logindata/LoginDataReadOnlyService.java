package com.memory.beautifulbride.service.logindata;

import com.memory.beautifulbride.repository.logindata.LoginDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginDataReadOnlyService {
    private final LoginDataRepository loginDataRepository;
    private final PasswordEncoder passwordEncoder;

    private final Random random = new Random();

    /** 이미 아이디가 존재하는지 체크하기 위한 메서드 입니다. */
    public ResponseEntity<String> checkIfAccountIdExists(String loginId) {
        return loginDataRepository.existsByLoginId(loginId)
                ? ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디 입니다.")
                : ResponseEntity.ok().body("사용 가능한 아이디 입니다.");
    }

    /** 이미 이메일이 존재하는지 체크하기 위한 메서드 입니다. */
    public ResponseEntity<String> checkIfAccountEmailExists(String loginEmail) {
        return loginDataRepository.existsByLoginEmail(loginEmail)
                ? ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일 입니다.")
                : ResponseEntity.ok().body("사용 가능한 이메일 입니다.");
    }

    /** 아이디를 찾기 위한 메서드 입니다. */
    public String getAccountId(String loginEmail) {
        return loginDataRepository.findLoginIdByEmail(loginEmail);
    }

    public ResponseEntity<String> checkAccountIdandEmail(String loginId, String loginEmail) {
        if (loginDataRepository.existsByLoginId(loginId) && loginDataRepository.existsByLoginEmail(loginEmail)){
            return ResponseEntity.ok().body("존재하는 ID,Email 입니다");
        }
        return  ResponseEntity.status(HttpStatus.CONFLICT).body("존재하지 않는 id 입니다");
    }
}
