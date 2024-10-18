package io.github.yeqk97.dlykserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_permission", schema = "dlyk")
public class TPermission {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "code", length = 30)
    private String code;

    @Column(name = "url", length = 30)
    private String url;

    @Column(name = "type", length = 30)
    private String type;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "order_no")
    private Integer orderNo;

    @Column(name = "icon", length = 100)
    private String icon;

}