package com.memory.beautifulbride.dtos;

import lombok.Builder;

@Builder
public record CompanySignupDTO(
        String loginId,
        String loginPwd,
        String loginEmail,
        String companyName,
        String companyPhone,
        String businessNumber,
        String address
) {
}
