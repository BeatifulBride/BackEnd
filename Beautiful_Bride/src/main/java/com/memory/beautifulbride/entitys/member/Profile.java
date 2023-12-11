package com.memory.beautifulbride.entitys.member;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;


@Entity
@Table(name = "TBL_MEM_PROFILE")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Profile {
    @Id
    @Column(name = "PROFILE_INDEX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected final int profileIndex = 0;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEM_NO")
    private Member member;

    @Column(name = "MEM_NAME")
    private String memName;

    @Column(name = "MEM_PHONE")
    private String memPhone;

    @Column(name = "MEM_WEDDINGDATE")
    private Date memWeddingDate;

    @Column(name = "UPLOAD_DATE")
    @CreatedDate
    @LastModifiedDate
    protected Date uploadDate;
}
