package com.memory.beautifulbride.dtos.dress;

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
        String dressExplanation
) {
}
