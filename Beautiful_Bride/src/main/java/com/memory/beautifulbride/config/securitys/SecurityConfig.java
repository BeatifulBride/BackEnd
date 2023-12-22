package com.memory.beautifulbride.config.securitys;

import com.memory.beautifulbride.config.jwt.JwtAEntryPoint;
import com.memory.beautifulbride.config.jwt.JwtFilter;
import com.memory.beautifulbride.config.jwt.TokenProvider;
import com.memory.beautifulbride.entitys.logindata.BasicsKinds;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAEntryPoint jwtAEntryPoint;

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
                        registry.requestMatchers("/admin/**").hasRole(BasicsKinds.ADMIN.toString())
                                .anyRequest().permitAll()
                );
        return httpSecurity.build();
    }


    @RequiredArgsConstructor
    @Component
    public static class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

        private final TokenProvider tokenProvider;

        @Override
        public void configure(HttpSecurity httpSecurity) throws Exception {
            JwtFilter customFilter = JwtFilter.builder()
                    .tokenProvider(tokenProvider)
                    .ignorePaths(
                            "/auth/signup", "/auth/login", "/docs/**",
                            "/swagger-ui/**", "/wdimg/**", "/mimg/**"
                    )
                    .build();

            httpSecurity.addFilterAfter(customFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests(request -> request.requestMatchers(customFilter.getIgnorePaths()).permitAll());
        }
    }
}
