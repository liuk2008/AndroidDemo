package com.android.common.net.error;

/**
 * 自定义业务异常
 */
public class ErrorException extends RuntimeException {

    private int code;
    private String message;

    public ErrorException(int resultCode, String message) {
        super(message);
        this.code = resultCode;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

