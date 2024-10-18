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
@Table(name = "t_clue", schema = "dlyk")
public class TClue {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private io.github.yeqk97.dlykserver.model.TUser owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private TActivity activity;

    @Column(name = "full_name", length = 64)
    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appellation")
    private io.github.yeqk97.dlykserver.model.TDicValue appellation;

    @Column(name = "phone", length = 18)
    private String phone;

    @Column(name = "weixin", length = 128)
    private String weixin;

    @Column(name = "qq", length = 20)
    private String qq;

    @Column(name = "email", length = 128)
    private String email;

    @Column(name = "age")
    private Integer age;

    @Column(name = "job", length = 64)
    private String job;

    @Column(name = "year_income", precision = 10, scale = 2)
    private BigDecimal yearIncome;

    @Column(name = "address", length = 128)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "need_loan")
    private io.github.yeqk97.dlykserver.model.TDicValue needLoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intention_state")
    private io.github.yeqk97.dlykserver.model.TDicValue intentionState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intention_product")
    private io.github.yeqk97.dlykserver.model.TProduct intentionProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state")
    private io.github.yeqk97.dlykserver.model.TDicValue state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source")
    private io.github.yeqk97.dlykserver.model.TDicValue source;

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