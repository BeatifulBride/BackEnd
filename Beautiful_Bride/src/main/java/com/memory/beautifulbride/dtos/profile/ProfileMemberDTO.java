package com.memory.beautifulbride.dtos.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileMemberDTO {
    private String memName;
    private Date weddingDate;
    private List<DressMarkDataDTO> dressMarkDataList;
    private List<TryOnDataDTO> tryOnImageDataList;

    @Builder(toBuilder = true)
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DressMarkDataDTO {
        private Integer dressInfoIndex;
        private String dressName;
        private String dressImagePath;
        private String dressPNumber;
        private String companyName;
    }

    @Builder(toBuilder = true)
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TryOnDataDTO {
        private String tryOnPath;
        private String dressPNumber;
        private String companyName;
    }
}
