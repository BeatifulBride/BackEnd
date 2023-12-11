package com.memory.beautifulbride.repository.member;

import com.memory.beautifulbride.entitys.member.Profile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
