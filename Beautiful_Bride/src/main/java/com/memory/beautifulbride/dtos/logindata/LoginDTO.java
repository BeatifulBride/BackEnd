package com.memory.beautifulbride.dtos.logindata;

import lombok.Builder;

@Builder(toBuilder = true)
public record LoginDTO (
        String LOGIN_ID,
        String LOGIN_PWD
) {
}
