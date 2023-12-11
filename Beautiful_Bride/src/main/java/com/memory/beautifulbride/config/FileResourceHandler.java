package com.memory.beautifulbride.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class FileResourceHandler implements WebMvcConfigurer {

    @Value("${ImgPath}")
    private String IMG_FOLDER;

    @Value("${WeddingDressImgPath}")
    private String WeddingDressImgPath;

    @Value("${MemberImgPath}")
    private String MemberImgPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String img_path = "file://" + IMG_FOLDER;

        registry.addResourceHandler("/imgs/**")
                .addResourceLocations(img_path);

        registry.addResourceHandler("/imgs" + WeddingDressImgPath)
                .addResourceLocations(img_path + WeddingDressImgPath);

        registry.addResourceHandler("/imgs" + MemberImgPath)
                .addResourceLocations(img_path + MemberImgPath);
    }
}
