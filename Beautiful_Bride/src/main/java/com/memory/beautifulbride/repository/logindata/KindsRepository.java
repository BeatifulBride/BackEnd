package com.memory.beautifulbride.repository.logindata;

import com.memory.beautifulbride.entitys.logindata.BasicsKinds;
import com.memory.beautifulbride.entitys.logindata.KindsTBL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KindsRepository extends JpaRepository<KindsTBL, BasicsKinds> {
    boolean existsByBasicsKinds(BasicsKinds basicsKinds);
}
