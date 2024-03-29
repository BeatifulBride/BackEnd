package com.memory.beautifulbride.entitys.company;

import com.memory.beautifulbride.entitys.dress.DressInfo;
import com.memory.beautifulbride.entitys.logindata.AbstractLoginDataMapping;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "TBL_COMPANY")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Company extends AbstractLoginDataMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_NO")
    protected final int companyNo = 0;

    // LOGIN_DATA :: AbstractNeedDate

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "PHONE")
    private String companyPhone;

    @Column(name = "BUSINESS_NUMBER")
    private String businessNumber;

    @Column(name = "ADDRESS")
    private String address;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<DressInfo> dressImages;
}
