package io.github.yeqk97.dlykserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "login_act", length = 32)
    private String loginAct;

    @JsonIgnore
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by")
    private TUser createBy;

    @Column(name = "edit_time")
    private Instant editTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edit_by")
    private TUser editBy;

    @Column(name = "last_login_time")
    private Instant lastLoginTime;

    @ManyToMany
    @JoinTable(
            name = "t_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<TRole> roles = new ArrayList<>();

    @Transient
    private List<String> permissionList = new ArrayList<>();

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        this.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole())));
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

    @JsonIgnore
    public boolean isAdmin() {
        return roles.stream().anyMatch(r -> r.getRole().equals("admin"));
    }
}