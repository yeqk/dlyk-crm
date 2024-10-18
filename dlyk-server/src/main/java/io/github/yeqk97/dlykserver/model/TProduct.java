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
@Table(name = "t_product", schema = "dlyk")
public class TProduct {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 128)
    private String name;

    @Column(name = "guide_price_s", precision = 10, scale = 2)
    private BigDecimal guidePriceS;

    @Column(name = "guide_price_e", precision = 10, scale = 2)
    private BigDecimal guidePriceE;

    @Column(name = "quotation", precision = 10, scale = 2)
    private BigDecimal quotation;

    @Column(name = "state")
    private Integer state;

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