package com.memory.beautifulbride.imgsavehandler.create;

import com.memory.beautifulbride.imgsavehandler.delete.DeleteOptions;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CreateDirectories {
    public void doCreateDirectory(Path directory, DeleteOptions options) throws IOException {

        // 파일 생성 시작 할것
        /*Files.walkFileTree(directory);*/
    }

    private FileVisitor<Path> createFolderFileVisitor() {
        return new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return super.preVisitDirectory(dir, attrs);
            }
        };
    }
}
