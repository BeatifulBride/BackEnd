package com.memory.beautifulbride.dtos.dress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DressListPageDTO {
    private int dressIndex;
    private String dressImagePath;
    private String dressName;
    private String companyName;
}
