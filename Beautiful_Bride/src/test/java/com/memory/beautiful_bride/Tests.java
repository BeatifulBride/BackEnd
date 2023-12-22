package beautiful_bride;

import com.memory.beautifulbride.dtos.dress.DressNewRegistrationDTO;
import com.memory.beautifulbride.entitys.dress.definition.*;
import com.memory.beautifulbride.service.dress.DressCommandService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
class Tests {

    @Autowired
    private DressCommandService dressCommandService;

    @org.junit.jupiter.api.Test
    @DisplayName("새 드레스 등록")
    void newDressTest() {
        MockMultipartFile front = multipartFile("D:\\Heoap\\WorkSpace\\AI_X\\BeautifulBride\\Beautiful_Bride\\src\\test\\java\\testImg\\test1.jpg", "front");
        MockMultipartFile side = multipartFile( "D:\\Heoap\\WorkSpace\\AI_X\\BeautifulBride\\Beautiful_Bride\\src\\test\\java\\testImg\\test2.png", "side");
        MockMultipartFile back = multipartFile( "D:\\Heoap\\WorkSpace\\AI_X\\BeautifulBride\\Beautiful_Bride\\src\\test\\java\\testImg\\test3.png", "back");

        DressNewRegistrationDTO dto = DressNewRegistrationDTO.builder()
                .front(front)
                .side(side)
                .back(back)
                .dressName("dresstestname")
                .dressPNumber("dresstestpname")
                .designer("dresstestdesigner")
                .dressPrice(10000)
                .dressLineEnum(DressLineEnum.A)
                .dressMaterialEnum(DressMaterialEnum.CRYSTAL)
                .dressNeckLineEnum(DressNeckLineEnum.V)
                .dressSleeveEnum(DressSleeveEnum.NONE)
                .dressFabricEnum(DressFabricEnums.ORGANZA)
                .dressLengthEnum(DressLengthEnums.FULL)
                .dressSeasonEnum(DressSeasonEnum.FALL)
                .dressExplanation("dresstestteststsetaestsejktajselkt")
                .build();

        dressCommandService.dressNewRegistration("com1", dto);
    }

    private MockMultipartFile multipartFile(String filePath, String originName) {
        try (InputStream stream = new FileInputStream(filePath)) {
            return new MockMultipartFile(filePath, originName, "jpg", stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
