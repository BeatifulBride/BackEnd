package com.memory.beautifulbride.service.logindata;

import com.memory.beautifulbride.entitys.basics.LoginData;
import com.memory.beautifulbride.repository.basics.LoginDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginDataReadOnlyService {
    private final LoginDataRepository loginDataRepository;
    private final PasswordEncoder passwordEncoder;

    private final Random random = new Random();

    public ResponseEntity<String> checkIfAccountIdExists(String loginId) {
        return loginDataRepository.findByLoginId(loginId)
                .map(x -> new ResponseEntity<>("이미 존재하는 아이디 입니다.", HttpStatus.CONFLICT))
                .orElseGet(() -> new ResponseEntity<>("사용 가능한 아이디 입니다.", HttpStatus.OK));
    }

    public String getAccountId(String loginEmail) {
        return loginDataRepository.findByLoginEmail(loginEmail);
    }

    public String reAccountPwd(String loginId, String loginEmail) {
        LoginData loginData = loginDataRepository.findByLoginData(loginId, loginEmail);
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[10];
        secureRandom.nextBytes(bytes);
        String newPwd = new BigInteger(1, bytes).toString(16);

        loginDataRepository.saveAndFlush(
                loginData.toBuilder()
                        .loginPwd(passwordEncoder.encode(newPwd))
                        .build()
        );
        return newPwd;
    }
}
