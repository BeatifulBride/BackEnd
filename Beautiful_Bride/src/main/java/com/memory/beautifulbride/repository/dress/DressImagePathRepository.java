package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.entitys.dress.DressImagePath;
import com.memory.beautifulbride.entitys.dress.DressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DressImagePathRepository extends JpaRepository<DressImagePath, Integer> {
    @Query("delete DressImagePath d where d.dressInfo=:dressInfo")
    @Modifying
    void dressImagePathDelete(@Param("dressInfo") DressInfo dressInfo);
}
