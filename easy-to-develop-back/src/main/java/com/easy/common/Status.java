package com.easy.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    // 通用请求状态
    SUCCESS(200, "成功"),
    ERROR(400, "未知错误"),
    ID_IS_NULL(400, "id为空"),
    DATA_IS_NULL(400, "数据为空"),


    // 数据库操作异常
    FAILED_TO_ADD(400, "添加失败"),
    FAILED_TO_DELETE(400, "删除失败"),
    NOT_EXISTS(400, "数据不存在"),
    FAILED_TO_UPDATE(400, "更新失败"),

    // 用户请求状态
    USER_EXISTS(400, "用户已存在"),
    USER_NOT_EXISTS(400, "用户不存在"),
    PASSWORD_ERROR(400, "密码错误"),
    UNAUTHORIZED(401, "权限不足"),
    FORBIDDEN(403, "禁止访问"),

    // try捕获异常
    FAILED_TO_GET_PRIVATE_KEY(500, "获取密钥失败"),
    PARAMETER_CONVERSION_FAILED(500, "参数转换异常"),
    CLASS_NOT_FOUND(500, "类未找到"),
    FAILED_TO_GET_OBJECT(500, "获取对象失败"),
    ;


    private final int code;
    private final String message;

}
