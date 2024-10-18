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

@Getter
@Setter
@Entity
@Table(name = "t_dic_value", schema = "dlyk")
public class TDicValue {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_code", referencedColumnName = "type_code")
    private TDicType typeCode;

    @Column(name = "type_value", length = 64)
    private String typeValue;

    @Column(name = "`order`")
    private Integer order;

    @Column(name = "remark", length = 64)
    private String remark;

}