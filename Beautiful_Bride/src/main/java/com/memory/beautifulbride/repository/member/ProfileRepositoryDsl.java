package com.memory.beautifulbride.repository.member;

import com.memory.beautifulbride.dtos.ProfileMainInfoDTO;

import java.security.Principal;

public interface ProfileRepositoryDsl {
    ProfileMainInfoDTO getMemMainInfo(Principal principal);
}
