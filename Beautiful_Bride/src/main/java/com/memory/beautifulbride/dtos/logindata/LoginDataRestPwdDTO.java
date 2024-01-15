package com.memory.beautifulbride.dtos.logindata;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder(toBuilder = true)
public record LoginDataRestPwdDTO(
        @NotBlank
        String loginId,
        @NotBlank @Email
        String loginEmail,
        @NotBlank
        String newPwd
) {
}
