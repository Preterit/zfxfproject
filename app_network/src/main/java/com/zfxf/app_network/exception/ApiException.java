package com.zfxf.app_network.exception;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public class ApiException extends Exception {
    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
