package com.memory.beautifulbride.service.dress;

import com.memory.beautifulbride.dtos.dress.DressListPageDTO;
import com.memory.beautifulbride.repository.dress.DressInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DressReadOnlyService {
    private final DressInfoRepository dressInfoRepository;


    public DressListPageDTO getDressdetailinfo(String i){
        return null;
    }

    public List<DressListPageDTO> getAllDresses(){
        //이미지 front 데이터와 함께 전달해주어야 한다
        return dressInfoRepository.getAllDresses();
    }

    public List<DressListPageDTO> getTop5Dresses(){
        return dressInfoRepository.getTop5Dresses();
    }
    public List<DressListPageDTO> getcommpanyTop5Dresses(String companyName){
        return dressInfoRepository.getcommpanyTop5Dresses(companyName);
    }


}
