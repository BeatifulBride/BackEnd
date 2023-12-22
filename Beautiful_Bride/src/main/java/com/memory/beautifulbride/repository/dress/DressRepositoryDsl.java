package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.dtos.dress.DressNewRegistrationDTO;
import org.springframework.http.ResponseEntity;

public interface DressRepositoryDsl {
    /**새 드레스를 등록하기 위한 메서드*/
    ResponseEntity<String> dressNewRegistration(DressNewRegistrationDTO dressNewRegistrationDTO);


}
