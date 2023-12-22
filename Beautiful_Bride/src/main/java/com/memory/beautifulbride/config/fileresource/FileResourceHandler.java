package com.memory.beautifulbride.config.fileresource;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class FileResourceHandler implements WebMvcConfigurer {

    @Value("${ImgPath}")
    private String imgFolder;

    @Value("${WeddingDressImgPath}")
    private String weddingDressImgPath;

    @Value("${MemberImgPath}")
    private String memberImgPath;

    String resourcePreviousWord = "/imgs/";

    @Getter
    private SimpleMvcResource weddingDress;
    @Getter
    private SimpleMvcResource memberImg;

    @PostConstruct
    public void init() {
        weddingDress = new SimpleMvcResource(imgFolder, weddingDressImgPath, resourcePreviousWord);
        memberImg = new SimpleMvcResource(imgFolder, memberImgPath, resourcePreviousWord);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(resourcePreviousWord + "**")
                .addResourceLocations("file://" + imgFolder);

        registry.addResourceHandler(weddingDress.getResourcePath())
                .addResourceLocations(weddingDress.getFileAbsolutePath());

        registry.addResourceHandler(memberImg.getResourcePath())
                .addResourceLocations(memberImg.getFileAbsolutePath());
    }
}
