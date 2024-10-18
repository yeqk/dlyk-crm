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

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_clue_remark", schema = "dlyk")
public class TClueRemark {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clue_id")
    private TClue clue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_way")
    private io.github.yeqk97.dlykserver.model.TDicValue noteWay;

    @Column(name = "note_content")
    private String noteContent;

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

    @Column(name = "deleted")
    private Integer deleted;

}