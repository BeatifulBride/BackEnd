package com.memory.beautifulbride.config.jwt;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Log4j2
@NoArgsConstructor
public class SecurityUtil {
    public static Optional<String> getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.error("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails springSecurityUser) {
            username = springSecurityUser.getUsername();

        } else if (principal instanceof String) {
            username = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(username);
    }
}
