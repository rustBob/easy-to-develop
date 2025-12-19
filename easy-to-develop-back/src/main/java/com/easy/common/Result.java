package com.easy.common;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class Result<T> implements Serializable {

    @Serial
    private final static long serialVersionUID = 1L;

    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data){
        return Result.<T>builder()
                .code(Status.SUCCESS.getCode())
                .message(Status.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> Result<T> success(){
        return success(null);
    }

    public static <T> Result<T> error(Status status){
        return Result.<T>builder()
                .code(status.getCode())
                .message(status.getMessage())
                .build();
    }

    public static <T> Result<T> error(int code, String message){
        return Result.<T>builder()
                .code(code)
                .message(message)
                .build();
    }
}
