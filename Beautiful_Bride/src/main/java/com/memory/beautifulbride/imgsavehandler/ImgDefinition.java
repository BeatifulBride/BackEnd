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

    /**
     * 이미지 이름과 확장자를 반환 하기 위한 메서드 입니다.
     *
     * @return ex : cat.jpg
     */
    public String getImageNameWithExtension() {
        return this.imgName + "." + this.imgExtension;
    }

    /**
     * 이미지가 저장되어야 할 폴더의 위치를 반환합니다.
     *
     * @return ex : C:\BeautifulB\Test\Imgs
     */
    public Path getSavePath() {
        return switch (pathType) {
            case ABSOLUTE -> PathUtil.getAbsolutePath(this.savePath);
            case RELATIVE -> this.savePath;
        };
    }

    /**
     * 모든 값을 반환합니다. Files.copy를 위한 값입니다. 그 외에도 비슷한 모든 정의에 사용 가능합니다.
     * <pre>{@code
     * Files.copy(stream, definition.getFullImagePath());
     * }</pre>
     *
     * @return C:\BeautifulB\Test\Imgs\cat.jpg
     */
    public Path getFullImagePath() {
        return getSavePath().resolve(getImageNameWithExtension());
    }
}
