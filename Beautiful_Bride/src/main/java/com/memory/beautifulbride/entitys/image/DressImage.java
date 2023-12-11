package com.memory.beautifulbride.entitys.image;

import com.memory.beautifulbride.entitys.company.Company;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;

@Entity
@Table(name = "TBL_DRESS_IMAGE")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DressImage {
    @Id
    @Column(name = "DRESS_INDEX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected final int dressIndex = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMAPNY_NO")
    private Company company;

    @Column(name = "DRESS_PHOTO_PATH")
    private String dressPhotoPath;

    @Column(name = "DRESS_P_NUMBER")
    private String dressPNumber;

    @Column(name = "DRESS_PRICE")
    private int dressPrice;

    @Column(name = "UPLOAD_DATE")
    @LastModifiedDate
    private Date uploadDate;

    @OneToOne
    private DressMarkCount dressMarkCount;
}
