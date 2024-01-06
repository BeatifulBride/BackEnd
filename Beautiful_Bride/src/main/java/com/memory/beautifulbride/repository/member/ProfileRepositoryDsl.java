package com.memory.beautifulbride.repository.member;

import com.memory.beautifulbride.dtos.profile.ProfileMainInfoDTO;
import com.memory.beautifulbride.dtos.profile.ProfileMemberDTO;

import java.util.Optional;

public interface ProfileRepositoryDsl {
    Optional<ProfileMainInfoDTO> getProfileMainInfo(String loginId);

    Optional<ProfileMemberDTO> getProfileMyPageInfo(String loginId);
}
