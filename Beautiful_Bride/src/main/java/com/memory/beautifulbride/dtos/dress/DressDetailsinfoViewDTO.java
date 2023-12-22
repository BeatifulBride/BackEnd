package com.memory.beautifulbride.dtos.dress;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder(toBuilder = true)
public class DressDetailsinfoViewDTO {
    private MultipartFile front;
    private MultipartFile side;
    private MultipartFile back;

    private String imagepath;
    private String company;
    private String dressName;
    private String dressPNumber;
    private String designer;
    private int dressPrice;
    private String dressExplanation;
}
