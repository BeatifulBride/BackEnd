package com.memory.beautifulbride.dtos.dress;

import lombok.Builder;

@Builder(toBuilder = true)
public class DressMyPageViewDTO {
    private int dressIndex;
    private String dressPath;
    private String dressName;
    private String companyName;
    private String companyAddress;
    private int price;
}
