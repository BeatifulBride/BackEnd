package com.memory.beautifulbride.dtos.logindata;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder(toBuilder = true)
public record LoginDTO(
        @NotBlank @Pattern(regexp = "^[A-Za-z0-9]*$", message = "영어 알파벳만 허용됩니다.")
        String LOGIN_ID,
        @NotBlank @Pattern(regexp = "^[A-Za-z0-9]{4,}$", message = "비밀번호는 최소 8자 이상이며, 영어 대소문자와 숫자를 포함할 수 있습니다.")
        String LOGIN_PWD
) {
}
