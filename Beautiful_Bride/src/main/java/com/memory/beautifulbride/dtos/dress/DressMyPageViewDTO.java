package com.memory.beautifulbride.dtos.dress;

import com.memory.beautifulbride.entitys.dress.definition.DressLineEnum;
import lombok.Builder;

@Builder(toBuilder = true)
public class DressMyPageViewDTO {
    private int dressIndex;
    private String dressPath;
    private String dressName;
    private DressLineEnum dressLine;
    private String companyName;
    private String companyAddress;
    private int price;
}
