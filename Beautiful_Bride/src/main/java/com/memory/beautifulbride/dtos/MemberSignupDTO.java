package com.memory.beautifulbride.dtos;

import lombok.Builder;

import java.sql.Date;

@Builder
public record MemberSignupDTO(
        String loginId,
        String loginPwd,
        String loginEmail,
        String memName,
        String memPhone,
        String memWeddingDate
) {
}
