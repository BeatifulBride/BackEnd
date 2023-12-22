package com.memory.beautifulbride.entitys.member;

import com.memory.beautifulbride.entitys.logindata.AbstractLoginDataMapping;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "TBL_MEMBER")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractLoginDataMapping {

    @Id
    @Column(name = "MEM_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int memNo = 0;

    // LOGIN :: AbstractNeedDate

    @OneToOne(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "PROFILE_INDEX")
    private Profile profile;
}
