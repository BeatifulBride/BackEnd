package com.memory.beautifulbride.config.jwt;

import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.repository.logindata.LoginDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {
    private final LoginDataRepository loginDataRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginDataId) throws UsernameNotFoundException {
        return loginDataRepository.findByLoginId(loginDataId)
                .map(this::createUser)
                .orElseThrow(() -> new UsernameNotFoundException(loginDataId + " :: 데이터베이스에서 찾을 수 없습니다."));
    }

    private User createUser(LoginData loginData) {
        String authority = loginData.getKinds().getBasicsKinds().name();
        List<GrantedAuthority> grantedAuthorities = Collections
                .singletonList(new SimpleGrantedAuthority(authority));

        return new User(loginData.getLoginId(), loginData.getLoginPwd(), grantedAuthorities);
    }
}
