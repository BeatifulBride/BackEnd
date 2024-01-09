package com.memory.beautifulbride.repository.member;

import com.memory.beautifulbride.dtos.member.MemberMyMarkDTO;
import com.memory.beautifulbride.entitys.dress.DressInfo;
import com.memory.beautifulbride.entitys.logindata.LoginData;
import com.memory.beautifulbride.entitys.member.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

interface ProfileDressMarkRepositoryDsl {
    ResponseEntity<String> dressMark(LoginData loginData, int dressInfoIndex);

    boolean notLimitMark(LoginData loginData);

    boolean alreadyMark(DressInfo dressInfo, Profile profile);

    List<MemberMyMarkDTO> findMyMarkListDTO(UserDetails userDetails, List<Integer> markIndexList);
}
