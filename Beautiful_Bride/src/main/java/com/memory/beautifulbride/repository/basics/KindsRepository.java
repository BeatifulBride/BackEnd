package com.memory.beautifulbride.repository.basics;

import com.memory.beautifulbride.entitys.basics.BasicsKinds;
import com.memory.beautifulbride.entitys.basics.KindsTBL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KindsRepository extends JpaRepository<KindsTBL, BasicsKinds> {
    boolean existsByBasicsKinds(BasicsKinds basicsKinds);
}
