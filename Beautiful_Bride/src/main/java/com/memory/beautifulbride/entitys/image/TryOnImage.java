package com.memory.beautifulbride.entitys.image;

import com.memory.beautifulbride.entitys.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_TRYON_IMAGE")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TryOnImage {
    @Id
    @Column(name = "TRYON_INDEX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected final int tryOnIndex = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMAPNY_NO")
    private Member member;

    @Column(name = "TRYON_PATH")
    private String string;
}
