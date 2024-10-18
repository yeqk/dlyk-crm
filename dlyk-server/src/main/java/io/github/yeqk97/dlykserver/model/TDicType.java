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
@Table(name = "t_dic_type", schema = "dlyk")
public class TDicType {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type_code", nullable = false, length = 64)
    private String typeCode;

    @Column(name = "type_name", length = 64)
    private String typeName;

    @Column(name = "remark", length = 128)
    private String remark;

}