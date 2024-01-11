package com.memory.beautifulbride.dtos.dress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder(toBuilder = true)
public record DressNewRegistrationDTO(
        MultipartFile front,
        MultipartFile side,
        MultipartFile back,
        @NotBlank
        String dressName,
        @NotBlank
        String dressPNumber,
        @NotBlank
        String designer,
        @NotNull
        int dressPrice,
        @NotBlank
        String dressExplanation
) {
}
