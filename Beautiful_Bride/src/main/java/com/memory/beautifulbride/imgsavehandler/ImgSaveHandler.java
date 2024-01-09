package com.memory.beautifulbride.imgsavehandler;

import com.memory.beautifulbride.imgsavehandler.delete.DeleteDirectories;
import com.memory.beautifulbride.imgsavehandler.delete.DeleteOptions;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Objects;

@Log4j2
public class ImgSaveHandler {
    private final OSPermissions osPermissions = new OSPermissions();

    /**
     * 이미지를 저장하기 위한 메서드 입니다. 저장을 하기전 폴더가 존재하지 않는다면 생성을 시도합니다.
     *
     * @param definition  이미지 저장에 필요한 정보를 정의한 클래스입니다.
     * @param options     실패시 폴더를 삭제하기 위한 옵션입니다. 값이 존재할경우 폴더 삭제를 시도 합니다.
     *                    하지만 값이 존재하지 않으면 아무것도 하지 않습니다.
     * @apiNote {@link Files}, {@link FileVisitor}, {@link PosixFilePermission}
     */
    public void imgSave(
            MultipartFile multipartFile,
            @NotNull ImgDefinition definition,
            @Nullable DeleteOptions options) throws IOException {


        try (InputStream stream = multipartFile.getInputStream()) {
            createDirectories(definition.getSavePath());
            Files.copy(stream, definition.getFullImagePath());

        } catch (IOException e) {
            log.error("이미지 저장중 오류 발생 생성한 폴더 및 파일 삭제 시작");
            log.error("삭제할 대상의 위치는 {} 입니다.", definition.getSavePath());
            log.error(e.getMessage());
            DeleteDirectories.doDeleteDirectory(
                    definition.getSavePath(),
                    Objects.requireNonNullElse(options, DeleteOptions.ALL)
            );

            log.error("해당 폴더 및 삭제 완료");
            throw new IOException("이미지 저장 오류");
        }
    }

    /**
     * 폴더를 생성하기 위한 메서드 입니다. 폴더를 사전 방문후, 존재하지 않을 경우에만 생성을 시도합니다.
     *
     * @param directoryPath 생성할 폴더의 위치입니다.
     *
     *                      <p>directoryPath의 값을 C:\BeautifulB\Test 처럼 주어질 경우에는 C:\BeautifulB 경로에 Test 폴더 생성을 시도 합니다.</p>
     * @throws IOException 권한 시도에 실패 하거나, 해당 디렉토리에 생성할 권한이 없을 경우에 오류가 반환됩니다.
     */
    private void createDirectories(@NotNull Path directoryPath) throws IOException {
        if (Files.notExists(directoryPath)) Files.createDirectories(directoryPath);
    }
}

