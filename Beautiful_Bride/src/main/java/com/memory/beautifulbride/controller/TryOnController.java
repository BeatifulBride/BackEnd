package com.memory.beautifulbride.controller;

import com.memory.beautifulbride.dtos.tryon.TryOnDTO;
import com.memory.beautifulbride.service.tryon.TryOnCommandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tryon")
public class TryOnController {
    private final TryOnCommandService tryOnCommandService;
    ///tryon/${dreesIndex} 드레스 시착요청 GET
    //tryon/result 드레스 트라이온 결과 post

    @PostMapping(value ="/starttryon")
    @Operation(summary = "tryonbutton 사용 시 사용자가 선택한 드레스의 인덱스값과 사용자의 사진이 넘어간다")
    ResponseEntity<String> TryOnToAI(TryOnDTO tryOnDTO ){
        return tryOnCommandService.tryonsendtofastapi(tryOnDTO);
    }

//    @PostMapping(value ="/result",produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "드레스를 시착하게되면 착용한 드레스가 나타난다!")
//    ResponseEntity<String> getTryOnResult(@AuthenticationPrincipal Principal principal) {
//        return tryOnCommandService.tryonresult(tryOnDataDTO);
//    }



}
