package com.memory.beautifulbride.entitys.logindata;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_KINDS")
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class KindsTBL {
    @Id
    @Column(name = "KINDS")
    @Convert(converter = BasicsKinds.Convert.class)
    @Enumerated(EnumType.STRING)
    private BasicsKinds basicsKinds;
}
