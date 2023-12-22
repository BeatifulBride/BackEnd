package com.memory.beautifulbride.dtos.dress;

import com.memory.beautifulbride.entitys.dress.definition.*;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder(toBuilder = true)
public record DressNewRegistrationDTO(
        MultipartFile front,
        MultipartFile side,
        MultipartFile back,
        String dressName,
        String dressPNumber,
        String designer,
        int dressPrice,
        DressLineEnum dressLineEnum,
        DressNeckLineEnum dressNeckLineEnum,
        DressSleeveEnum dressSleeveEnum,
        DressFabricEnums dressFabricEnum,
        DressLengthEnums dressLengthEnum,
        DressMaterialEnum dressMaterialEnum,
        DressSeasonEnum dressSeasonEnum,
        String dressExplanation
) {
}
