package io.github.yeqk97.dlykserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_system_info", schema = "dlyk")
public class TSystemInfo {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "system_code", length = 45)
    private String systemCode;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "site", nullable = false, length = 100)
    private String site;

    @Column(name = "logo", length = 100)
    private String logo;

    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @Column(name = "description", nullable = false, length = 45)
    private String description;

    @Column(name = "keywords", nullable = false, length = 100)
    private String keywords;

    @Column(name = "shortcuticon", nullable = false, length = 100)
    private String shortcuticon;

    @Column(name = "tel", length = 100)
    private String tel;

    @Column(name = "weixin", length = 25)
    private String weixin;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "version", length = 145)
    private String version;

    @Column(name = "closeMsg", length = 500)
    private String closeMsg;

    @ColumnDefault("'y'")
    @Column(name = "isopen", length = 8)
    private String isopen;

    @Column(name = "create_time")
    private Instant createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by")
    private io.github.yeqk97.dlykserver.model.TUser createBy;

    @Column(name = "edit_time")
    private Instant editTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edit_by")
    private io.github.yeqk97.dlykserver.model.TUser editBy;

}