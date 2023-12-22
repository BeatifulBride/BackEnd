package com.memory.beautifulbride.entitys.dress;

import com.memory.beautifulbride.entitys.company.Company;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "TBL_DRESS_INFO")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DressInfo {
    @Id
    @Column(name = "DRESS_INFO_INDEX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int dressInfoIndex = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_NO")
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
    @CreatedDate
    @LastModifiedDate
    private Date uploadDate;

    @OneToOne(optional = false, cascade = CascadeType.ALL, targetEntity = DressMarkCount.class)
    @JoinColumn(name = "MARK_INDEX", referencedColumnName = "MARK_INDEX")
    private DressMarkCount dressMarkCount;

    @OneToMany(mappedBy = "dressInfo", cascade = CascadeType.ALL)
    private List<DressImagePath> dressImagePath;
}
