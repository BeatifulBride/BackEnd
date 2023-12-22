package com.memory.beautifulbride.controller;


import com.memory.beautifulbride.dtos.dress.DressListPageDTO;
import com.memory.beautifulbride.dtos.dress.DressNewRegistrationDTO;
import com.memory.beautifulbride.service.dress.DressCommandService;
import com.memory.beautifulbride.service.dress.DressReadOnlyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dress")
public class DressController {
    private final DressReadOnlyService dressReadOnlyService;
    private final DressCommandService dressCommandService;


    /**
     * @return 현재 등록된 Dress의 목록을 모두 반환한다
     * /dress/list 상품리스트 표시 get
     * db에 있는 값을 모두 반환? 이미지 데이터는 어떻게 반환할것인가?
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "등록된 드레스 목록을 반환합니다")
    ResponseEntity<List<DressListPageDTO>> getDressAllList() {
        try {
            List<DressListPageDTO> dresses = dressReadOnlyService.getAllDresses();
            System.out.println("Retrieved dresses:" + dresses);
            return ResponseEntity.ok(dresses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * @return
     */
    @GetMapping(value = "/top5/{commpanyName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "기업고객의 등록된 드레스 목록 상위 5개를 반환합니다")
    ResponseEntity<List<DressListPageDTO>> getcommpanyTop5Dresses(String commpanyName) {
        try {
            List<DressListPageDTO> dresses = dressReadOnlyService.getCompanyTop5Dresses(commpanyName);
            System.out.println("Retrieved dresses:" + dresses);
            return ResponseEntity.ok(dresses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/top5", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "전체 드레스에서 등록된 드레스 목록 상위 5개를 반환합니다")
    ResponseEntity<List<DressListPageDTO>> getTop5Dresses() {
        try {
            List<DressListPageDTO> dresses = dressReadOnlyService.getTop5Dresses();
            System.out.println("Retrieved dresses:" + dresses);
            return ResponseEntity.ok(dresses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping(value = "/info/{dressIndex}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "드레스의 상세한 정보를 가져옵니다")
    ResponseEntity<String> getDressDetailInfo(@PathVariable(name = "dressIndex") String dressIndex) {
        //인덱스는 형변환
        return null;
    }

    @PostMapping(value = "/newdress", produces = MediaType.MULTIPART_FORM_DATA_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "새 드레스를 등록 합니다.")
    ResponseEntity<String> dressNewRegistration(Principal principal, DressNewRegistrationDTO dto) {
        return dressCommandService.dressNewRegistration(principal.getName(), dto);
    }

    @PostMapping(value = "/newdressTest", produces = MediaType.MULTIPART_FORM_DATA_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "새 드레스를 등록 테스트 입니다. 임의의 업체 아이디를 받습니다..")
    ResponseEntity<String> dressNewRegistrationTEST(
            @RequestParam(name = "testAccountId") String testAccountId,
            @ParameterObject @ModelAttribute DressNewRegistrationDTO dto,
            @RequestPart("front") MultipartFile front,
            @RequestPart("side") MultipartFile side,
            @RequestPart("back") MultipartFile back
    ) {
        dto = dto.toBuilder().front(front).side(side).back(back).build();
        return dressCommandService.dressNewRegistration(testAccountId, dto);
    }
}
