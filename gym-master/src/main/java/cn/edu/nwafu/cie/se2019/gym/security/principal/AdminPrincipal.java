package cn.edu.nwafu.cie.se2019.gym.security.principal;

import cn.edu.nwafu.cie.se2019.gym.model.Admin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class AdminPrincipal implements UserDetails {
    private Long id;
    private String name;
    private String email;
    private String tel;
    @JsonIgnore
    private String password;

    public AdminPrincipal(Long id, String name,  String email, String tel, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.password = password;
    }

    public static AdminPrincipal create(Admin user) {
        return new AdminPrincipal(
                user.getAid(),
                user.getAname(),
                user.getAmail(),
                user.getAtel(),
                user.getApwd()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority ga = new SimpleGrantedAuthority("ROLE_ADMIN");
        return Collections.singletonList(ga);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminPrincipal that = (AdminPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
