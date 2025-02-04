package io.github.yeqk97.dlykserver.model.dto;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Data;

@Data
public class ActivityDto
{
    private Integer id;

    private String ownerName;

    private String name;

    private Instant startTime;

    private Instant endTime;

    private BigDecimal cost;

    private String description;

    private Instant createTime;

    private String createBy;

    private Instant editTime;

    private String editBy;
}
