package com.memory.beautifulbride.entitys.dress;

import com.memory.beautifulbride.entitys.member.Profile;
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
    @JoinColumn(name = "PROFILE_INDEX")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DRESS_INDEX")
    private DressInfo dressImage;

    @Column(name = "TRYON_PATH")
    private String tryOnPath;
}
