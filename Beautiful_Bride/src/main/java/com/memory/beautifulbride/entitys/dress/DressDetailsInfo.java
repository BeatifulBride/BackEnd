package com.memory.beautifulbride.entitys.dress;

import com.memory.beautifulbride.entitys.dress.definition.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_DRESS_DETAILS_INFO")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DressDetailsInfo {
    @Id
    @Column(name = "DRESS_INFO_INDEX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int dressDetailsIndex = 0;

    @OneToOne(optional = false, targetEntity = DressInfo.class)
    @JoinColumn(name = "DRESS_INDEX")
    private DressInfo dressInfo;

    @Column(name = "DRESS_LENGTH")
    @Convert(converter = DressLengthEnums.Convert.class)
    @Enumerated(EnumType.STRING)
    private DressLengthEnums dressLengthEnums;

    @Column(name = "DRESS_LINE")
    @Convert(converter = DressLengthEnums.Convert.class)
    @Enumerated(EnumType.STRING)
    private DressLineEnum dressLineEnum;

    @Column(name = "DRESS_MATERIAL")
    @Convert(converter = DressMaterialEnum.Convert.class)
    @Enumerated(EnumType.STRING)
    private DressMaterialEnum dressMaterialEnum;

    @Column(name = "DRESS_NECKLINE")
    @Convert(converter = DressNeckLineEnum.Convert.class)
    @Enumerated(EnumType.STRING)
    private DressNeckLineEnum dressNeckLineEnum;

    @Column(name = "DRESS_SLEEVE")
    @Convert(converter = DressSleeveEnum.Convert.class)
    @Enumerated(EnumType.STRING)
    private DressSleeveEnum dressSleeveEnum;
}
