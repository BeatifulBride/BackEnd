package com.memory.beautifulbride.entitys.dress;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_DRESS_MARK_COUNT")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DressMarkCount {
    @Id
    @Column(name = "MARK_INDEX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected final int markIndex = 0;

    @OneToOne(mappedBy = "dressMarkCount", fetch = FetchType.LAZY)
    @JoinColumn(name = "DRESS_INFO_INDEX")
    private DressInfo dressInfo;

    @Column(name = "MARK_COUNT")
    @Builder.Default
    private long markCount = 0;
}
