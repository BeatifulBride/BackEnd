package com.memory.beautifulbride.controller;


import com.memory.beautifulbride.service.dress.DressCommandService;
import com.memory.beautifulbride.service.dress.DressReadOnlyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @GetMapping(value="/list",produces =MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "등록된 드레스 목록을 반환합니다")
    ResponseEntity<String> getDressAllList(){
        return null;
    }

    /**
     * @return
     */
    @GetMapping(value="/top5",produces =MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "기업고객의 등록된 드레스 목록 상위 5개를 반환합니다")
    ResponseEntity<String> getDressTop5List(){
        return null;
    }

    @GetMapping(value="/info/{dressIndex}",produces =MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "드레스의 상세한 정보를 가져옵니다")
    ResponseEntity<String> getDressDetailInfo(@PathVariable(name="dressIndex")String dressIndex){
        //인덱스는 형변환
        return null;
    }

}
