package com.memory.beautifulbride.dtos.dress;

import com.memory.beautifulbride.entitys.dress.definition.*;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder(toBuilder = true)
public record DressNewRegistrationDTO(
        MultipartFile main,
        MultipartFile front,
        MultipartFile side,
        MultipartFile back,
        String dressName,
        Enum<DressLineEnum> dressLineEnum,
        Enum<DressMaterialEnum> materialEnum,
        Enum<DressNeckLineEnum> dressNeckLineEnum,
        Enum<DressLengthEnums> dressLengthEnums,
        Enum<DressSleeveEnum> dressSleeveEnum,
        String dressExplanation
) {
}
