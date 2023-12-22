package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.dtos.dress.DressListPageDTO;
import com.memory.beautifulbride.dtos.tryon.TryOnDTO;

import java.util.List;

public interface DressInfoRepositoryDsl {
    /**새 드레스를 등록하기 위한 메서드*/
//    ResponseEntity<String> dressNewRegistration(DressNewRegistrationDTO dressNewRegistrationDTO);
    List<DressListPageDTO> getAllDresses();
    List<DressListPageDTO> getcommpanyTop5Dresses(String companyName);
    List<DressListPageDTO> getTop5Dresses();




    String findByCompanyAndDressIndex(TryOnDTO tryOnDTO);

}
