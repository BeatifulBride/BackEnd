package com.memory.beautifulbride.service.dress;

import com.memory.beautifulbride.config.fileresource.FileResourceHandler;
import com.memory.beautifulbride.dtos.dress.DressNewRegistrationDTO;
import com.memory.beautifulbride.entitys.company.Company;
import com.memory.beautifulbride.entitys.dress.DressDetailsInfo;
import com.memory.beautifulbride.entitys.dress.DressImagePath;
import com.memory.beautifulbride.entitys.dress.DressInfo;
import com.memory.beautifulbride.imgsavehandler.ImgDefinition;
import com.memory.beautifulbride.imgsavehandler.ImgSaveHandler;
import com.memory.beautifulbride.imgsavehandler.PathType;
import com.memory.beautifulbride.imgsavehandler.delete.DeleteOptions;
import com.memory.beautifulbride.repository.company.CompanyRepository;
import com.memory.beautifulbride.repository.dress.DressDetailsInfoRepository;
import com.memory.beautifulbride.repository.dress.DressImagePathRepository;
import com.memory.beautifulbride.repository.dress.DressInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class DressCommandService {

    private final DressInfoRepository dressInfoRepository;
    private final DressDetailsInfoRepository dressDetailsInfoRepository;
    private final DressImagePathRepository dressImagePathRepository;
    private final CompanyRepository companyRepository;
    private final FileResourceHandler fileResourceHandler;
    private final ImgSaveHandler imgSaveHandler = new ImgSaveHandler();

    public ResponseEntity<String> dressNewRegistration(String companyId, DressNewRegistrationDTO dto) {

        Company company = companyRepository.searchFromLoginData(companyId);

        String absWeddingPath = fileResourceHandler.getWeddingDress().getAbsolutePath()
                + dto.dressPNumber() + "/";

        String resWeddingPath = fileResourceHandler.getWeddingDress().getResourcePath()
                + dto.dressPNumber() + "/";

        DressInfo newDressInfo = DressInfo.builder()
                .company(company)
                .designer(dto.designer())
                .dressName(dto.dressName())
                .dressPNumber(dto.dressPNumber())
                .dressPrice(dto.dressPrice())
                .build();

        DressInfo successInfo = dressInfoRepository.save(newDressInfo);

        try {
            dressDetailsInfoRepository.save(createDressDetailsInfo(dto, successInfo));

            List<DressImagePath> dressImagePath = createDressImagePathList(dto, newDressInfo, absWeddingPath, resWeddingPath);
            dressImagePathRepository.saveAll(dressImagePath);

            return ResponseEntity.ok().body("새 드레스 등록에 성공 하였습니다.");
        } catch (DataAccessException e) {
            log.error("새 드레스 등록에 실패하였습니다. 엔티티 객체가 잘못 생성 되었습니다. {}", e.getMessage());
            return ResponseEntity.unprocessableEntity().body("새 드레스 등록 실패 하였습니다.");
        }
    }

    private List<DressImagePath> createDressImagePathList(DressNewRegistrationDTO dto, DressInfo dressInfo, String absWeddingPath, String resWeddingPath) {
        // TODO 현재 드레스 폴더 중복 생성시 오류 처리에 대한것 설정 안했으니 할것
        Map<String, MultipartFile> map = Map.of(
                "front", dto.front(),
                "side", dto.side(),
                "back", dto.back()
        );

        return map.entrySet().stream().map(entry -> {

                    Path savePath = Path.of(absWeddingPath);
                    MultipartFile multipartFile = entry.getValue();

                    ImgDefinition definition = ImgDefinition.builder()
                            .savePath(savePath)
                            .imgName(entry.getKey())
                            .imgExtension(multipartFile.getContentType())
                            .pathType(PathType.ABSOLUTE)
                            .build();

                    try {
                        log.error("이미지 저장 시도중...");
                        imgSaveHandler.imgSave(multipartFile, definition, DeleteOptions.ALL, null);
                        log.error("이미지 저장 성공!!!");
                        return DressImagePath.builder()
                                .dressInfo(dressInfo)
                                .path(resWeddingPath)
                                .build();
                    } catch (IOException e) {
                        log.error("{} 이미지 저장에 실패하였습니다. 해당 경로 :: {}", entry.getKey(), e.getMessage());
                        log.error("해당 이미지의 이름은 {} 입니다", definition.getImageNameWithExtension());
                        throw new RuntimeException("실패하였습니다. 생성을 중지합니다.");
                    }
                })
                .toList();
    }

    private DressDetailsInfo createDressDetailsInfo(DressNewRegistrationDTO dto, DressInfo dressInfo) {
        return DressDetailsInfo.builder()
                .dressInfo(dressInfo)
                .dressSleeveEnum(dto.dressSleeveEnum())
                .dressMaterialEnum(dto.dressMaterialEnum())
                .dressLineEnum(dto.dressLineEnum())
                .dressLengthEnums(dto.dressLengthEnum())
                .dressNeckLineEnum(dto.dressNeckLineEnum())
                .dressFabricEnums(dto.dressFabricEnum())
                .build();
    }
}
