package io.github.yeqk97.dlykserver.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {

    OK(200, "成功"),

    FAIL(500, "失败"),

    TOKEN_EMPTY(901, "请求token为空"),

    TOKEN_INVALID(902, "请求token不合法"),

    TOKEN_EXPIRED(903, "请求token已过期"),

    TOKEN_NOT_MATCH(904, "请求jwt不正确"),

    ACCESS_DENIED(905, "权限不足");

    private final int code;

    private final String msg;
}