package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.entitys.dress.DressInfo;
import com.memory.beautifulbride.entitys.dress.DressMarkCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DressMarkCountRepository extends JpaRepository<DressMarkCount, Integer> {
    @Query("delete DressMarkCount d where d.dressInfo=:dressInfo")
    @Modifying
    void dressMarkCountDelete(@Param("dressInfo") DressInfo dressInfo);
}
