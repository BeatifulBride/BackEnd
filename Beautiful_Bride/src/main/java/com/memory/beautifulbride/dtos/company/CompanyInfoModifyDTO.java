package com.memory.beautifulbride.dtos.company;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CompanyInfoModifyDTO {
    @NotBlank
    private String companyName;
    @NotBlank
    private String companyAddress;
    @NotBlank
    private String companyPhone;
}
