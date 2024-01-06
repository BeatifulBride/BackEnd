package com.memory.beautifulbride.repository.member;

import com.memory.beautifulbride.entitys.dress.DressInfo;
import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.entitys.member.Profile;
import org.springframework.http.ResponseEntity;

interface ProfileDressMarkRepositoryDsl {
    ResponseEntity<String> dressMark(LoginData loginData, int dressInfoIndex);

    boolean notLimitMark(LoginData loginData);

    boolean alreadyMark(DressInfo dressInfo, Profile profile);
}
