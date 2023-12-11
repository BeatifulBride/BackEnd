package com.memory.beautifulbride.entitys.member;

import com.memory.beautifulbride.entitys.basics.AbstractNeedDate;
import com.memory.beautifulbride.entitys.image.TryOnImage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "TBL_MEMBER")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractNeedDate {

    @Id
    @Column(name = "MEM_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected final int memNo = 0;

    // LOGIN :: AbstractNeedDate

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "member"
    )
    @JoinColumn(name = "PROFILE_INDEX")
    private Profile profile;

    @OneToMany(mappedBy = "member")
    private List<TryOnImage> tryOnImages;
}
