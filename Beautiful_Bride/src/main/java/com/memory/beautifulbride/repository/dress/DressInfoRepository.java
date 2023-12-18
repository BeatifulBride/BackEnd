package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.entitys.dress.DressInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DressInfoRepository extends JpaRepository<DressInfo, Integer>, DressInfoRepositoryDsl {
}
