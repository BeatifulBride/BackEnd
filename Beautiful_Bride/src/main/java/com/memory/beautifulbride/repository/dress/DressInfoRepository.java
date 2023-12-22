package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.entitys.dress.DressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DressInfoRepository extends JpaRepository<DressInfo, Integer>, DressInfoRepositoryDsl {

    @Query("select count(*) from DressInfo d")
    int getAllDressInfoSize();
}
