package com.memory.beautifulbride.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class TokenPrincipal implements HandlerMethodArgumentResolver {

    private final UserDetailsService userDetailsService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserDetails.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            System.out.println(principal);
            return principal;
        } else if (principal instanceof String username) {
            System.out.println(username);
            return userDetailsService.loadUserByUsername(username);
        }
        throw new RuntimeException("사용자 인증이 필요 합니다.");
    }
}
