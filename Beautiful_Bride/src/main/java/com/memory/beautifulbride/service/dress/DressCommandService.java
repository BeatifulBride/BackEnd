package com.memory.beautifulbride.service.dress;

import com.memory.beautifulbride.config.fileresource.FileResourceHandler;
import com.memory.beautifulbride.config.fileresource.SimpleMvcResource;
import com.memory.beautifulbride.dtos.dress.DressNewRegistrationDTO;
import com.memory.beautifulbride.entitys.company.Company;
import com.memory.beautifulbride.entitys.dress.DressImagePath;
import com.memory.beautifulbride.entitys.dress.DressInfo;
import com.memory.beautifulbride.entitys.dress.DressMarkCount;
import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.imgsavehandler.ImgDefinition;
import com.memory.beautifulbride.imgsavehandler.ImgSaveHandler;
import com.memory.beautifulbride.imgsavehandler.PathType;
import com.memory.beautifulbride.imgsavehandler.delete.DeleteOptions;
import com.memory.beautifulbride.repository.company.CompanyRepository;
import com.memory.beautifulbride.repository.dress.DressImagePathRepository;
import com.memory.beautifulbride.repository.dress.DressInfoRepository;
import com.memory.beautifulbride.repository.dress.DressMarkCountRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class DressCommandService {

    private final DressInfoRepository dressInfoRepository;
    private final DressImagePathRepository dressImagePathRepository;
    private final DressMarkCountRepository dressMarkCountRepository;
    private final CompanyRepository companyRepository;
    private final FileResourceHandler fileResourceHandler;
    private final ImgSaveHandler imgSaveHandler = new ImgSaveHandler();
    private SimpleMvcResource weddingDressResource;

    @PostConstruct
    private void init() {
        this.weddingDressResource = fileResourceHandler.getWeddingDress();
    }

    public ResponseEntity<String> dressNewRegistration(String companyId, DressNewRegistrationDTO dto) {
        Company company;
        try {
            company = companyRepository.searchFromLoginData(companyId)
                    .orElseThrow(() -> new NotFoundException("업체 정보를 찾지 못했습니다."));
        } catch (NotFoundException e) {
            log.error("드레스 등록에서 업체가 아닌 유저가 요청 하였습니다. 요청 아이디 :: {}", companyId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업체 정보를 찾지 못했습니다.");
        }

        String fng = folderNameGenerator();
        String absWeddingPath = String.valueOf(weddingDressResource.createFileAbsPathWithLow(fng));
        String resWeddingPath = weddingDressResource.createFileResPathWithLow(fng);

        DressInfo newDressInfo = DressInfo.builder()
                .company(company)
                .designer(dto.designer())
                .dressName(dto.dressName())
                .dressPNumber(dto.dressPNumber())
                .dressPrice(dto.dressPrice())
                .dressMarkCount(DressMarkCount.builder().build())
                .uploadDate(Date.valueOf(LocalDate.now()))
                .build();

        try {
            dressInfoRepository.save(newDressInfo);

            List<DressImagePath> dressImagePath = createDressImagePathListAndImgSave(
                    dto, newDressInfo, absWeddingPath, resWeddingPath
            );

            dressImagePathRepository.saveAll(dressImagePath);

            return ResponseEntity.ok().body("새 드레스 등록에 성공 하였습니다.");
        } catch (DataAccessException e) {
            log.error("새 드레스 등록에 실패하였습니다. 엔티티 객체가 잘못 생성 되었습니다. {}", e.getMessage());
            return ResponseEntity.unprocessableEntity().body("새 드레스 등록 실패 하였습니다.");
        }
    }

    private List<DressImagePath> createDressImagePathListAndImgSave(DressNewRegistrationDTO dto, DressInfo dressInfo, String absWeddingPath, String resWeddingPath) {
        // TODO 현재 드레스 폴더 중복 생성시 오류 처리에 대한것 설정 안했으니 할것
        Map<String, MultipartFile> map = Map.of(
                "front", dto.front(),
                "side", dto.side(),
                "back", dto.back()
        );

        Path savePath = Path.of(absWeddingPath);

        return map.entrySet().stream().map(entry -> {
                    MultipartFile multipartFile = entry.getValue();
                    String extend = Objects.requireNonNull(multipartFile.getContentType()).split("/")[1];

                    ImgDefinition definition = ImgDefinition.builder()
                            .savePath(savePath)
                            .imgName(entry.getKey())
                            .imgExtension(extend)
                            .pathType(PathType.ABSOLUTE)
                            .build();

                    try {
                        imgSaveHandler.imgSave(multipartFile, definition, DeleteOptions.ALL);
                        return DressImagePath.builder()
                                .dressInfo(dressInfo)
                                .path(resWeddingPath + entry.getKey() + "." + extend)
                                .build();
                    } catch (IOException e) {

                        log.error("{} 이미지 저장에 실패하였습니다. 해당 경로 :: {}", entry.getKey(), e.getMessage());
                        log.error("해당 이미지의 이름은 {} 입니다", definition.getImageNameWithExtension());

                        throw new RuntimeException("실패하였습니다. 생성을 중지합니다.");
                    }
                })
                .toList();
    }


    public ResponseEntity<String> dressDelete(UserDetails userDetails, int dressIndex) {
        if (!LoginData.isCompanyMember(userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("업체 회원이 아닙니다.");
        }


        Company company = companyRepository.searchFromLoginData(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("업체 정보를 찾지 못했습니다."));

        DressInfo dressInfo = dressInfoRepository.getDressInfo(dressIndex)
                .orElseThrow(() -> new EntityNotFoundException("드레스 정보를 찾지 못했습니다."));


        if (!company.equals(dressInfo.getCompany())) {
            log.error("의심되는 요청입니다. 업체 정보와 드레스 소유 업체의 정보가 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("잘못된 요청입니다. 드레스의 소유 업체가 아닙니다.");
        }

        try {
            dressInfoRepository.delete(dressInfo);
        } catch (Exception e) {
            log.error("드레스 삭제에 실패했습니다. 잘못된 데이터입니다. {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("드레스 삭제에 실패했습니다. 잘못된 데이터입니다.");
        }

        return ResponseEntity.ok("해당 드레스가 삭제 되었습니다.");
    }

    private String folderNameGenerator() {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMdd_HHmmssSSS"));
        String randomStr = new Random().ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(6)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return dateTime + "_" + randomStr;
    }

    private Company companyOptionalCheck(Optional<Company> company) {
        try {
            company.orElseThrow(() -> new EntityNotFoundException("업체 정보를 찾지 못했습니다."));
        } catch (EntityNotFoundException e) {
            log.error("업체 정보를 찾지 못했습니다. {}", e.getMessage());
        }
        return company.get();
    }
}
