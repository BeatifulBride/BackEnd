package com.memory.beautifulbride.dtos.company;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder(toBuilder = true)
public record CompanySignupDTO(
        @NotBlank
        String loginId,
        @NotBlank
        String loginPwd,
        @NotBlank @Email
        String loginEmail,
        @NotBlank
        String companyName,
        @NotBlank
        String companyPhone,
        @NotBlank
        String businessNumber,
        @NotBlank
        String address
) {
}
