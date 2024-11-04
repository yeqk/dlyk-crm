package io.github.yeqk97.dlykserver.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Data
public class UserDto {
    private Integer id;

    private String loginAct;

    private String loginPwd;

    private String name;

    private String phone;

    private String email;

    private Integer accountNoExpired;

    private Integer credentialsNoExpired;

    private Integer accountNoLocked;

    private Integer accountEnabled;

    private Instant createTime;

    private String createBy;

    private Instant editTime;

    private String editBy;

    private Instant lastLoginTime;
}
