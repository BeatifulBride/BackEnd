package com.memory.beautifulbride.dtos.member;

import lombok.Builder;

@Builder(toBuilder = true)
public record MemberSignupDTO(
        String loginId,
        String loginPwd,
        String loginEmail,
        String memName,
        String memPhone,
        String memWeddingDate
) {
}
