package com.memory.beautifulbride.dtos.company;

import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CompanyMainPage {
    private String companyName;
    private String companyEmail;
    private String companyAddress;
    private String companyPhone;
    private int dressAllRegistrationCount;

    private thisCompanyDress latestUpload;
    private List<thisCompanyDress> companyTop5DressList;
    private List<thisCompanyDress> companyAllDressList;

    @Builder(toBuilder = true)
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class thisCompanyDress {
        private String dressImagePath;
        private String dressName;
        private int dressInfoIndex;
        private long markCount;
    }
}