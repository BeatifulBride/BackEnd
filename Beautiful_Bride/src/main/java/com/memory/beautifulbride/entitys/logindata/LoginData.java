package com.memory.beautifulbride.entitys.logindata;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

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

    @Column(name = "LOGIN_EMAIL")
    private String loginEmail;

    @ManyToOne
    @JoinColumn(name = "KINDS", referencedColumnName = "KINDS")
    private KindsTBL kinds;

    private static List<BasicsKinds> normalMember() {
        return List.of(BasicsKinds.FREE, BasicsKinds.CHARGED);
    }

    private static List<BasicsKinds> companyMember() {
        return List.of(BasicsKinds.COMPANY, BasicsKinds.ADMIN);
    }


    public boolean isNormalMember() {
        return normalMember().contains(this.kinds.getBasicsKinds());
    }

    public boolean isCompanyMember() {
        return companyMember().contains(this.kinds.getBasicsKinds());
    }

    public static boolean isCompanyMember(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            BasicsKinds basicsKinds = BasicsKinds.valueOf(authority.getAuthority().replace("ROLE_", ""));
            if (companyMember().contains(basicsKinds)) {
                return true;
            }
        }
        return false;
    }
}
