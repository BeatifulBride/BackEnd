package com.memory.beautifulbride.config;

import com.memory.beautifulbride.entitys.logindata.BasicsKinds;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .headers(header ->
                        header.frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(registry ->
                        registry.requestMatchers("/admin/**").hasRole(BasicsKinds.ADMIN.toString())
                                //.requestMatchers("/tryon/**", "/mem/**").authenticated()
                                .anyRequest().permitAll()
                )

                .formLogin(formLogin ->
                        formLogin.loginPage("/auth/loginfrom")
                                .loginProcessingUrl("/auth/login")
                                .usernameParameter("LOGIN_ID")
                                .passwordParameter("LOGIN_PWD")
                                .defaultSuccessUrl("/")
                )

                /*.exceptionHandling(exception ->
                        exception.accessDeniedPage("/accessDenied")
                )*/

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
