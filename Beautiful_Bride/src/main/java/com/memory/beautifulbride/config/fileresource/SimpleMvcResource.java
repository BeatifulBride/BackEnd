package com.memory.beautifulbride.config.fileresource;

import lombok.Getter;

public class SimpleMvcResource {
    @Getter
    private String absolutePath;
    private String resourcePath;
    @Getter
    private String pathPatterns;

    public SimpleMvcResource(String absolutePath, String resourcePath, String pathPatterns) {
        this.absolutePath = absolutePath;
        this.resourcePath = resourcePath;
        this.pathPatterns = pathPatterns;
    }

    public String getFileAbsolutePath() {
        return "file://" + this.absolutePath;
    }

    public String getResourcePath() {
        return this.pathPatterns + this.resourcePath;
    }
}
