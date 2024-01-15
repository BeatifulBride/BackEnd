package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.entitys.dress.DressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DressInfoRepository extends JpaRepository<DressInfo, Integer>, DressInfoRepositoryDsl {

    @Query("select count(*) from DressInfo d")
    int getAllDressInfoSize();

    @Query("select d from DressInfo d where d.dressInfoIndex=:dressInfoIndex")
    Optional<DressInfo> getDressInfo(@Param("dressInfoIndex") int dressInfoIndex);
}
