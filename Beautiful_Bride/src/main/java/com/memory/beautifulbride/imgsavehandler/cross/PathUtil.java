package com.memory.beautifulbride.imgsavehandler.cross;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtil {
    public static Path getAbsolutePath(Path relativePath) {
        String os = System.getProperty("os.name").toLowerCase();
        String pathString = String.valueOf(relativePath);

        if (os.contains("win")) {
            return Paths.get("C:\\", pathString);
        } else {
            return Paths.get("/", pathString);
        }
    }
}
