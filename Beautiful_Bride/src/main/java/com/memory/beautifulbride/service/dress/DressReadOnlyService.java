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

    public List<DressListPageDTO> getAllDresses() {
        return dressInfoRepository.getAllDresses();
    }

    public List<DressListPageDTO> getTop5Dresses() {
        return dressInfoRepository.getTop5Dresses();
    }
}
