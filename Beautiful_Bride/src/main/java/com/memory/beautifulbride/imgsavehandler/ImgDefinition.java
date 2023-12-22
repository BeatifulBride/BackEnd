package com.memory.beautifulbride.imgsavehandler;

import com.memory.beautifulbride.imgsavehandler.cross.PathUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.nio.file.Path;

/** 이미지 저장을 위한 값들을 정의하기 위한 클래스 입니다. */
@AllArgsConstructor
@Builder
public class ImgDefinition {

    private Path savePath;
    private String imgName;
    private String imgExtension;
    private PathType pathType;

    public String getImageNameWithExtension() {
        return this.imgName +"."+ this.imgExtension;
    }

    public Path getSavePath() {
        return switch (pathType) {
            case ABSOLUTE -> PathUtil.getAbsolutePath(this.savePath);
            case RELATIVE -> this.savePath;
        };
    }

    public Path getFullImagePath() {
        return getSavePath().resolve(getImageNameWithExtension());
    }
}
