package com.memory.beautifulbride.dtos.dress;

import com.memory.beautifulbride.entitys.dress.definition.DressLineEnum;
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
    private String dressPath;
    private String dressName;
    private DressLineEnum dressLine;
    private String companyName;
}
