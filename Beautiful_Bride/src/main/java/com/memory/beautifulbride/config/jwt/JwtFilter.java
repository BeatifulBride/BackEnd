package com.memory.beautifulbride.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Log4j2
@Builder
public class JwtFilter extends GenericFilterBean {

    public static String authorizationHeader = "Authorization";

    private TokenProvider tokenProvider;

    @Getter
    private String[] ignorePaths;

    public static JwtFilterBuilder builder() {
        return new JwtFilterBuilder();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        boolean notIgnore = Arrays.stream(ignorePaths).filter(re -> !re.isBlank())
                .noneMatch(requestURI::matches);

        if (notIgnore) {
            if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
                Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.error("유효한 JWT 토큰이 없습니다, uri : {}", requestURI);
            }
        }
        chain.doFilter(request, response);
    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(authorizationHeader);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static class JwtFilterBuilder {
        private TokenProvider tokenProvider;

        private String[] ignorePaths;

        JwtFilterBuilder() {
        }

        public JwtFilterBuilder tokenProvider(TokenProvider tokenProvider) {
            this.tokenProvider = tokenProvider;
            return this;
        }

        public JwtFilterBuilder ignorePaths(String... ignorePaths) {
            this.ignorePaths = (String[]) Objects.requireNonNullElse(ignorePaths, "");
            return this;
        }

        public JwtFilter build() {
            return new JwtFilter(this.tokenProvider, this.ignorePaths);
        }
    }
}
