package com.memory.beautifulbride.dtos.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Builder(toBuilder = true)
public record MemberSignupDTO(
        @NotBlank
        String loginId,
        @NotBlank
        String loginPwd,
        @NotBlank @Email
        String loginEmail,
        @NotBlank
        String memName,
        @NotBlank
        @Pattern(regexp = "(\\d{2,3}-\\d{3,4}-\\d{3,4})", message = "전화번호는 'xxx-xxxx-xxxx' 또는 'xx-xxx-xxx' 형식이어야 합니다.")
        String memPhone,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        Date memWeddingDate
) {
}
