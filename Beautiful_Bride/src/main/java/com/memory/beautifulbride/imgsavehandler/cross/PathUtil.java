package com.memory.beautifulbride.imgsavehandler.cross;

import java.nio.file.Path;
import java.nio.file.Paths;

/**경로를 위한 유틸 클래스 입니다.*/
public class PathUtil {
    /**Os에 따라 절대 경로 Path값을 반환 합니다.*/
    public static Path getAbsolutePath(Path relativePath) {
        return convertAbsPath(relativePath);
    }

    /**Os에 따라 절대 경로 Path값을 반환 합니다.*/
    public static Path getAbsolutePath(String relativePath) {
        return convertAbsPath(Path.of(relativePath));
    }

    /**실제로 변환하는 메서드*/
    private static Path convertAbsPath(Path relativePath) {
        String os = System.getProperty("os.name").toLowerCase();
        String pathString = String.valueOf(relativePath);

        if (os.contains("win")) {
            if (!pathString.startsWith("C:\\")) {
                return Paths.get("C:\\", pathString);
            }
            
            return relativePath;
        } else {
            if (!pathString.startsWith("/")) return Paths.get("/", pathString);
        }

        return relativePath;
    }
}
