package com.memory.beautifulbride.config.fileresource;

import com.memory.beautifulbride.imgsavehandler.cross.PathUtil;
import lombok.Getter;

import java.io.File;
import java.nio.file.Path;

@Getter
public class SimpleMvcResource {
    private final Path absolutePath;
    private final String resourcePath;
    private final String s = File.separator;

    public SimpleMvcResource(Path absolutePath, String resourcePath) {
        this.absolutePath = PathUtil.getAbsolutePath(absolutePath);
        this.resourcePath = resourcePath;
    }

    public String getFileAbsolutePath() {
        return this.absolutePath.toUri().toString();
    }

    public Path createFileAbsPathWithLow(String low) {
        return Path.of(getAbsolutePath() + s + low);
    }

    public String createFileResPathWithLow(String low) {
        return getResourcePath() + low + "/";
    }
}
