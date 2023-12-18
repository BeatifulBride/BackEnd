package com.memory.beautifulbride.entitys.dress;

import com.memory.beautifulbride.entitys.company.Company;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "TBL_DRESS_INFO")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DressInfo {
    @Id
    @Column(name = "DRESS_INDEX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int dressIndex = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMAPNY_NO")
    private Company company;

    @Column(name = "DRESS_DESIGNER")
    private String designer;

    @Column(name = "DRESS_NAME")
    private String dressName;

    @Column(name = "DRESS_P_NUMBER")
    private String dressPNumber;

    @Column(name = "DRESS_PRICE")
    private int dressPrice;

    @Column(name = "UPLOAD_DATE")
    @LastModifiedDate
    private Date uploadDate;

    /*@OneToOne
    private DressMarkCount dressMarkCount;*/

    @OneToMany(mappedBy = "dressInfo", cascade = CascadeType.ALL)
    private List<DressImagePath> dressImagePath;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = DressDetailsInfo.class)
    private DressDetailsInfo dressDetailsInfo;
}
