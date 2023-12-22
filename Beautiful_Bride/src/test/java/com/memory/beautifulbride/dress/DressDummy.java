package com.memory.beautifulbride.dress;

import com.memory.beautifulbride.dtos.dress.DressNewRegistrationDTO;
import com.memory.beautifulbride.repository.dress.DressInfoRepository;
import com.memory.beautifulbride.service.dress.DressCommandService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Random;

@SpringBootTest
public class DressDummy {

    @Autowired
    private DressCommandService dressCommandService;
    @Autowired
    private DressInfoRepository dressInfoRepository;
    private final Random random = new Random();

    @Test
    void dressDummy() {
        DressNewRegistrationDTO dto;

        File fFront = new File(Path.of("C:\\Users\\hi\\Downloads\\front.png").toUri());
        File fSide = new File(Path.of("C:\\Users\\hi\\Downloads\\side.png").toUri());
        File fBack = new File(Path.of("C:\\Users\\hi\\Downloads\\back.png").toUri());


        MultipartFile front = imgDummy(fFront, "front", "img/png");
        MultipartFile side = imgDummy(fSide, "side", "img/png");
        MultipartFile back = imgDummy(fBack, "back", "img/png");

        for (int i = 0; i < 100; i++) {
            dto = DressNewRegistrationDTO.builder()
                    .front(front)
                    .side(side)
                    .back(back)
                    .dressPNumber(String.valueOf(random.nextLong(999999999)))
                    .dressName("TestDressName %s".formatted(i))
                    .dressPrice(random.nextInt(999999999))
                    .dressExplanation("TestDressExplanation %s".formatted(i))
                    .designer("TestDressDesigner %s".formatted(i))
                    .build();

            dressCommandService.dressNewRegistration("company1", dto);
        }
    }

    @Test
    @Transactional
    void setDressPopularityRandom() {
        for (int i = 0; i < 30; i++) {
            int randomSelect = random.nextInt(dressInfoRepository.getAllDressInfoSize());
            dressInfoRepository.updateDressMarkCount(randomSelect, random.nextInt(451123));
        }
    }


    private MockMultipartFile imgDummy(File file, String name, String extend) {
        try (InputStream inputStream = new FileInputStream(file)) {
            return new MockMultipartFile(name, file.getName(), extend, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
