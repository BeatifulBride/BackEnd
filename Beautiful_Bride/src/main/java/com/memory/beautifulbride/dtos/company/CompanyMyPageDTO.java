package com.memory.beautifulbride.dtos.company;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
public record CompanyMyPageDTO(
        String companyName,
        String companyEmail,
        String companyAddress,
        String companyPhone
) {
}
