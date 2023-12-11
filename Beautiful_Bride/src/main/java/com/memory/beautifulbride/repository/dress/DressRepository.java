package com.memory.beautifulbride.repository.dress;

import com.memory.beautifulbride.entitys.dress.DressInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DressRepository extends JpaRepository<DressInfo, Integer>, DressRepositoryDsl {
}
