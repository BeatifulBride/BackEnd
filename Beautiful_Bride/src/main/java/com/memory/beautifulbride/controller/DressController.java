package com.memory.beautifulbride.controller;


import com.memory.beautifulbride.dtos.dress.DressListPageDTO;
import com.memory.beautifulbride.dtos.dress.DressNewRegistrationDTO;
import com.memory.beautifulbride.service.dress.DressCommandService;
import com.memory.beautifulbride.service.dress.DressReadOnlyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dress")
@Log4j2
public class DressController {
    private final DressReadOnlyService dressReadOnlyService;
    private final DressCommandService dressCommandService;


    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "등록된 드레스 목록을 반환합니다")
    ResponseEntity<List<DressListPageDTO>> getDressAllList() {
        try {
            List<DressListPageDTO> dresses = dressReadOnlyService.getAllDresses();
            return ResponseEntity.ok(dresses);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/top5", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "전체 드레스에서 등록된 드레스 목록 상위 5개를 반환합니다")
    ResponseEntity<List<DressListPageDTO>> getTop5Dresses() {
        try {
            List<DressListPageDTO> dresses = dressReadOnlyService.getTop5Dresses();
            return ResponseEntity.ok(dresses);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/newdress", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "새 드레스를 등록 합니다.", security = @SecurityRequirement(name = "Authorization"))
    ResponseEntity<String> dressNewRegistration(@Parameter(hidden = true) UserDetails userDetails, @Validated @ModelAttribute @RequestPart DressNewRegistrationDTO dto) {
        return dressCommandService.dressNewRegistration(userDetails.getUsername(), dto);
    }

    @DeleteMapping("/del/{dressIndex}")
    @Operation(summary = "드레스 삭제 요청입니다. 해당되는 업체 회원이 아닐 경우에 반려됩니다.", security = @SecurityRequirement(name = "Authorization"))
    ResponseEntity<String> dressDelete(@Parameter(hidden = true) UserDetails userDetails, @PathVariable("dressIndex") @NotNull int dressIndex) {
        return dressCommandService.dressDelete(userDetails, dressIndex);
    }
}
