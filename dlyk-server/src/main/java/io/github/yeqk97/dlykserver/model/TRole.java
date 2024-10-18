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
@Table(name = "t_role", schema = "dlyk")
public class TRole {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "role", length = 30)
    private String role;

    @Column(name = "role_name", length = 30)
    private String roleName;

}