package com.memory.beautifulbride.service.member;

import com.memory.beautifulbride.repository.member.MemberRepository;
import com.memory.beautifulbride.repository.member.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberReadOnlyService {

    private MemberRepository memberRepository;
    private ProfileRepository profileRepository;
}

