package com.memory.beautifulbride.service;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.attribute.PosixFilePermission.*;

@Log4j2
public class ImgSaveHandler {

    public enum DeleteOptions {ALL, FOLDER, FILE}

    /**
     * 이미지를 저장하기 위한 메서드 입니다. 저장을 하기전 폴더가 존재하지 않는다면 생성을 시도합니다.
     *
     * @param savePath    파일을 저장할 경로입니다.
     * @param permissions 폴더가 존재하지 않아 폴더 생성을 시도시 생성되는 폴더의 권한 설정입니다.
     * @param options     실패시 폴더를 삭제하기 위한 옵션입니다. 값이 존재할경우 폴더 삭제를 시도 합니다.
     *                    하지만 값이 존재하지 않으면 아무것도 하지 않습니다.
     * @apiNote {@link Files}, {@link FileVisitor}, {@link PosixFilePermission}
     */
    public void imgSave(
            MultipartFile multipartFile,
            @NotNull Path savePath,
            @Nullable DeleteOptions options,
            @Nullable PosixFilePermission... permissions) throws IOException {

        try (InputStream stream = multipartFile.getInputStream()) {
            createDirectories(savePath, permissions);
            Files.copy(stream, savePath);
        } catch (IOException e) {

            log.error(e.getMessage());

            if (options != null) {
                deleteDirectory(savePath, options);
            }
        }
    }

    /**
     * 권한을 설정하고 폴더를 생성하기 위한 메서드 입니다.
     *
     * @param directoryPath 폴더 생성 위치입니다.
     * @param permissions   폴더 생성시 권한 설정 입니다. 기본 설정값은 [764 : xwr | wr | r] 입니다.
     */
    private void createDirectories(Path directoryPath, @Nullable PosixFilePermission... permissions) {
        FileAttribute<?> fileAttribute = PosixFilePermissions
                .asFileAttribute(createPermissions(permissions));

        try {
            Files.createDirectories(directoryPath, fileAttribute);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 특정 파일, 폴더를 생성시 권한을 설정하기위한 메서드 입니다.
     * 매개 변수가 없을 경우에는 [764 : xwr | wr | r] 를 반환합니다.
     *
     * @param permissions 권한을 직접 설정하기 위한 설정입니다.
     */
    private Set<PosixFilePermission> createPermissions(@Nullable PosixFilePermission... permissions) {

        if (permissions != null) {
            return EnumSet.copyOf(Arrays.asList(permissions));
        } else {
            EnumSet<PosixFilePermission> ownerRWX = EnumSet.of(OWNER_EXECUTE, OWNER_WRITE, OWNER_READ);
            EnumSet<PosixFilePermission> groupRW = EnumSet.of(GROUP_WRITE, GROUP_READ);
            EnumSet<PosixFilePermission> othersR = EnumSet.of(OTHERS_READ);

            return Stream.of(ownerRWX, groupRW, othersR)
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
        }
    }

    /** 옵션에 따라서 삭제 방식을 결정하는 메서드 입니다. */
    private void deleteDirectory(Path directory, DeleteOptions options) throws IOException {

        FileVisitor<Path> fileVisitors = switch (options) {
            case FILE -> delFilesFileVisitor();
            case FOLDER -> delFoldersFileVisitor();
            case ALL -> delAllFileVisitor();
        };

        Files.walkFileTree(directory, fileVisitors);
    }

    /** 하위 디렉토리의 모든 폴더, 파일을 삭제하기 위한 {@link FileVisitor}의 정의 방식입니다. */
    private FileVisitor<Path> delAllFileVisitor() {
        return new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        };
    }

    /** 하위 디렉토리의 빈폴더만을 삭제하기 위한 {@link FileVisitor}의 정의 방식입니다. */
    private FileVisitor<Path> delFoldersFileVisitor() {
        return new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        };
    }

    /** 하위 디렉토리의 파일만을 삭제하기 위한 {@link FileVisitor}의 정의 방식입니다. */
    private FileVisitor<Path> delFilesFileVisitor() {
        return new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }
        };
    }
}

