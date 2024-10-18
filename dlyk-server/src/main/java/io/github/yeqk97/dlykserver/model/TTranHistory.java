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

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_tran_history", schema = "dlyk")
public class TTranHistory {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tran_id")
    private TTran tran;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage")
    private TDicValue stage;

    @Column(name = "money", precision = 10, scale = 2)
    private BigDecimal money;

    @Column(name = "expected_date")
    private Instant expectedDate;

    @Column(name = "create_time")
    private Instant createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by")
    private io.github.yeqk97.dlykserver.model.TUser createBy;

}