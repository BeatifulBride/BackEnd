package com.memory.beautifulbride.dtos.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberMyMarkDTO {
    String dressImagePath;
    int dressIndex;
    String dressCompanyName;
    String dressCompanyAddress;
    int dressPrice;
}
