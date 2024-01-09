package com.memory.beautifulbride.config.securitys;

import com.memory.beautifulbride.config.jwt.JwtAEntryPoint;
import com.memory.beautifulbride.config.jwt.JwtFilter;
import com.memory.beautifulbride.config.jwt.TokenPrincipal;
import com.memory.beautifulbride.config.jwt.TokenProvider;
import com.memory.beautifulbride.entitys.logindata.BasicsKinds;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .headers(header ->
                        header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(registry ->
                        registry.requestMatchers("admin/**").hasRole(BasicsKinds.ADMIN.toString())
                                .requestMatchers("mem/mypage", "mem/mark/**").authenticated()
                                .anyRequest().permitAll()
                )
                .with(new JwtSecurityConfig(tokenProvider), Customizer.withDefaults());
        return httpSecurity.build();
    }


    @RequiredArgsConstructor
    public static class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

        private final TokenProvider tokenProvider;

        @Override
        public void configure(HttpSecurity httpSecurity) {
            JwtFilter customFilter = JwtFilter.builder()
                    .tokenProvider(tokenProvider)
                    .build();
            httpSecurity.addFilterAfter(customFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Configuration
    public static class WebConfig implements WebMvcConfigurer {
        @Autowired
        private TokenPrincipal principal;
        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
            resolvers.add(principal);
        }
    }
}
