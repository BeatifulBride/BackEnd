package com.memory.beautifulbride.entitys.dress;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_DRESS_IMAGE_PATH")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DressImagePath {
    @Id
    @Column(name = "PATH_INDEX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int pathIndex = 0;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DRESS_INFO_INDEX")
    private DressInfo dressInfo;

    @Column(name = "PATH_VALUE")
    private String path;
}
