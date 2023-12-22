package com.memory.beautifulbride.dtos.member;

import lombok.Builder;

import java.sql.Date;

@Builder(toBuilder = true)
public record MemberSignupDTO(
        String loginId,
        String loginPwd,
        String loginEmail,
        String memName,
        String memPhone,
        Date memWeddingDate
) {
}
