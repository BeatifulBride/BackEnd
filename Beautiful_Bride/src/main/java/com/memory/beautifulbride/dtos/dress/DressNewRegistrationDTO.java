package com.memory.beautifulbride.dtos.dress;

import com.memory.beautifulbride.entitys.dress.definition.*;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder(toBuilder = true)
public record DressNewRegistrationDTO(
        MultipartFile main,
        MultipartFile front,
        MultipartFile side,
        MultipartFile back,
        String dressName,
        String brand,
        String designer,
        DressFabricEnums dressFabricEnums,
        DressLengthEnums dressLengthEnums,
        DressLineEnum dressLineEnum,
        DressMaterialEnum materialEnum,
        DressNeckLineEnum dressNeckLineEnum,
        DressSeasonEnum dressSeasonEnum,
        DressSleeveEnum dressSleeveEnum,
        String dressExplanation
) {
}
