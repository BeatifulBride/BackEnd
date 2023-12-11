package com.memory.beautifulbride.entitys.basics;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TBL_LOGIN_DATA")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginData {
    @Id
    @Column(name = "LOGIN_INDEX")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected final int loginIndex = 0;

    @Column(name = "LOGIN_ID", unique = true)
    private String loginId;

    @Column(name = "LOGIN_PWD")
    private String loginPwd;

    @Column(name = "EMAIL")
    private String loginEmail;

    @ManyToOne
    @JoinColumn(name = "KINDS", referencedColumnName = "KINDS")
    private KindsTBL kinds;
}
