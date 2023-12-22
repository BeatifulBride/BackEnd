package com.memory.beautifulbride.config.fileresource;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;


@Configuration
public class FileResourceHandler implements WebMvcConfigurer {

    @Value("${WeddingDressAbsPath}")
    private Path weddingDressAbsPath;
    @Value("${WeddingDressResPath}")
    private String weddingDressResPath;

    @Value("${MemberImgPath}")
    private Path memberImgAbsPath;
    @Value("${MemberImgResPath}")
    private String memberImgResPath;

    @Getter
    private SimpleMvcResource weddingDress;
    @Getter
    private SimpleMvcResource memberImg;

    /**외부에서 사용하기 위한 정의*/
    @PostConstruct
    public void init() {
        weddingDress = new SimpleMvcResource(weddingDressAbsPath, weddingDressResPath);
        memberImg = new SimpleMvcResource(memberImgAbsPath, memberImgResPath);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(weddingDress.getResourcePath() + "**")
                .addResourceLocations(weddingDress.getAbsolutePath().toUri().toString());

        registry.addResourceHandler(memberImg.getResourcePath() + "**")
                .addResourceLocations(memberImg.getAbsolutePath().toUri().toString());
    }
}
