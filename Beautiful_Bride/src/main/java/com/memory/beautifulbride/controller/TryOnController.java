package com.memory.beautifulbride.controller;

import com.memory.beautifulbride.service.tryon.TryOnCommandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tryon")
public class TryOnController {
    private final TryOnCommandService tryOnCommandService;
    ///tryon/${dreesIndex} 드레스 시착요청 GET
    //tryon/result 드레스 트라이온 결과 post

//
    @GetMapping(value ="/tryon/{dressIndex}/{memberprofilephoto}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "tryonbutton 사용 시 사용자가 선택한 드레스의 인덱스값과 사용자의 사진이 넘어간다")
    ResponseEntity<String> TryOnToAI(@PathVariable(name = "dressIndex") String dressIndex,
                                     @PathVariable(name = "memberprofilephoto") MultipartFile memberprofilephto,
                                     Principal principal) {
        return null;
        //여기서 문제는 파일을 함수로 건네주면서 fastapi에서 처리 후 응답이 자동으로 처리되는 부분의 로직도 같이 개발해주어야 한다
        //과연 파일처리에 대한 응답을 어떻게 처리할 것인가?
        //이 부분에 대한 로직처리의 고민을 해보자



    }

    @PostMapping(value ="/tryon/result",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "드레스를 시착하게되면 사용자의 선택한 드레스의 인덱스 값이 넘어간다")
    ResponseEntity<String> getTryOnResult(Principal principal) {
        return null;
    }



}
