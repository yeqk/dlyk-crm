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
@Table(name = "t_tran", schema = "dlyk")
public class TTran {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "tran_no")
    private String tranNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private TCustomer customer;

    @Column(name = "money", precision = 10, scale = 2)
    private BigDecimal money;

    @Column(name = "expected_date")
    private Instant expectedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage")
    private TDicValue stage;

    @Column(name = "description")
    private String description;

    @Column(name = "next_contact_time")
    private Instant nextContactTime;

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