package com.memory.beautifulbride.dtos.logindata;

import lombok.Builder;

@Builder(toBuilder = true)
public record LoginDataRestPwdDTO(
        String loginId,
        String loginEmail,
        String newPwd
) {
}
