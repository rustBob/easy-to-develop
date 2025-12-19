package com.easy.common.exception;

import com.easy.common.Status;
import lombok.Getter;

@Getter
public class AppException extends Exception{
    private final int code;
    private final String message;

    public AppException(int code, String message){
        this.code = code;
        this.message = message;
    }

    public AppException(Status status){
        this(status.getCode(), status.getMessage());
    }

    public AppException(){
        this(Status.ERROR);
    }
}
