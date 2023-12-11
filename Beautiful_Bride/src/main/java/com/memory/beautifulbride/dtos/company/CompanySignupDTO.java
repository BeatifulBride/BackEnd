package com.memory.beautifulbride.dtos.company;

import lombok.Builder;

@Builder(toBuilder = true)
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
