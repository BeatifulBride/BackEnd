package com.memory.beautifulbride.imgsavehandler.delete;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@Log4j2 @NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteDirectories {
    /** 옵션에 따라서 삭제 방식을 결정하는 메서드 입니다. */
    public static void doDeleteDirectory(Path directory, DeleteOptions options) throws IOException {
        log.debug("삭제 프로토콜 시작");
        FileVisitor<Path> fileVisitors = switch (options) {
            case FILE -> delFilesFileVisitor();
            case FOLDER -> delFoldersFileVisitor();
            case ALL -> delAllFileVisitor();
        };

        Files.walkFileTree(directory, fileVisitors);
    }

    /** 하위 디렉토리의 모든 폴더, 파일을 삭제하기 위한 {@link FileVisitor}의 정의 방식입니다. */
    private static FileVisitor<Path> delAllFileVisitor() {
        return new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.deleteIfExists(dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.SKIP_SUBTREE;
            }
        };
    }

    /** 하위 디렉토리의 빈폴더만을 삭제하기 위한 {@link FileVisitor}의 정의 방식입니다. */
    private static FileVisitor<Path> delFoldersFileVisitor() {
        return new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                    if (!stream.iterator().hasNext()) {
                        Files.deleteIfExists(dir);
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                log.error("해당 폴더나 파일이 존재하지 않습니다.");
                return FileVisitResult.SKIP_SUBTREE;
            }
        };
    }

    /** 하위 디렉토리의 파일만을 삭제하기 위한 {@link FileVisitor}의 정의 방식입니다. */
    private static FileVisitor<Path> delFilesFileVisitor() {
        return new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                log.error("해당 폴더나 파일이 존재하지 않습니다.");
                return FileVisitResult.SKIP_SUBTREE;
            }
        };
    }
}
