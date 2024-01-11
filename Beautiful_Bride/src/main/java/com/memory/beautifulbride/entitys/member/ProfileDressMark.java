package com.memory.beautifulbride.entitys.member;

import com.memory.beautifulbride.entitys.dress.DressInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_PROFILE_DRESS_MARK")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileDressMark {
    @Id
    @Column(name = "MARK_INDEX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long markIndex = 0L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROFILE_INDEX")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DRESS_INFO_INDEX")
    private DressInfo dressInfo;
}
