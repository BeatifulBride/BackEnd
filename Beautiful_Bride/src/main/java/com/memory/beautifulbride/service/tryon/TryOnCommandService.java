package com.memory.beautifulbride.service.tryon;

import com.memory.beautifulbride.dtos.tryon.TryOnDTO;
import com.memory.beautifulbride.repository.dress.DressInfoRepository;
import com.memory.beautifulbride.repository.tryon.TryOnRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.memory.beautifulbride.dtos.profile.ProfileMemberDTO.TryOnDataDTO;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Log4j2
public class TryOnCommandService {
    private final DressInfoRepository dressInfoRepository;
    private final TryOnRepository tryOnRepository;

    public ResponseEntity<String> tryonsendtofastapi(TryOnDTO tryOnDTO){
        //먼저 dresscommpany와 드레스 인덱스를 검사해서
        //해당되는 db의 image 경로를 찾아가 객체화한다
        //그럼 해당되는 db의 image 경로를 먼저 불러온다
        //그리고 경로를 따라가 해당 이미지를 불러온다
        //두 파일이 준비되면 fastapi로 전송한다
        //파일 찾는 로직
        //로컬에 저장되어 있는 파일을 찾아서 불러온다
        String dressFilePath = dressInfoRepository.findByCompanyAndDressIndex(tryOnDTO);
        MultipartFile profileDataPath = tryOnDTO.getMultipartFile();

        return null;
    }

    public ResponseEntity<String> tryonreuslt(TryOnDataDTO tryOnDataDTO){
        return null;
    }
    public ResponseEntity<String> setTryOnDress(){
        return null;
    }
}
