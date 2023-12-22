package com.memory.beautifulbride.dtos.tryon;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TryOnDTO {
    private MultipartFile multipartFile;
    private String commpanyName;
    private int dressIndex;

}
