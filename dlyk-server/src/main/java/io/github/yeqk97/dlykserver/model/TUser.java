package io.github.yeqk97.dlykserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "t_user", schema = "dlyk")
public class TUser implements UserDetails {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "login_act", length = 32)
    private String loginAct;

    @Column(name = "login_pwd", length = 64)
    private String loginPwd;

    @Column(name = "name", length = 32)
    private String name;

    @Column(name = "phone", length = 18)
    private String phone;

    @Column(name = "email", length = 64)
    private String email;

    @Column(name = "account_no_expired")
    private Integer accountNoExpired;

    @Column(name = "credentials_no_expired")
    private Integer credentialsNoExpired;

    @Column(name = "account_no_locked")
    private Integer accountNoLocked;

    @Column(name = "account_enabled")
    private Integer accountEnabled;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "edit_time")
    private Instant editTime;

    @Column(name = "edit_by")
    private Integer editBy;

    @Column(name = "last_login_time")
    private Instant lastLoginTime;

    @Transient
    private List<String> roleList = new ArrayList<>();

    @Transient
    private List<String> permissionList = new ArrayList<>();

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.getRoleList().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        this.getPermissionList().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.getLoginPwd();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.getLoginAct();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.getAccountNoExpired() == 1;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.getAccountNoLocked() == 1;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return this.getCredentialsNoExpired() == 1;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.getAccountEnabled() == 1;
    }
}