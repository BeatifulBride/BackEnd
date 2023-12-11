package com.memory.beautifulbride.dtos.dress;

import com.memory.beautifulbride.entitys.dress.definition.DressLineEnum;
import lombok.Builder;

@Builder(toBuilder = true)
public class DressListPageDTO {
    private int dressIndex;
    private String dressPath;
    private String dressName;
    private Enum<DressLineEnum> dressLine;
    private String companyName;
}
