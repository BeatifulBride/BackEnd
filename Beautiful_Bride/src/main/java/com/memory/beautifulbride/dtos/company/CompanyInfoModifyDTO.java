package com.memory.beautifulbride.dtos.company;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CompanyInfoModifyDTO {
    private String companyName;
    private String companyAddress;
    private String companyPhone;
}
