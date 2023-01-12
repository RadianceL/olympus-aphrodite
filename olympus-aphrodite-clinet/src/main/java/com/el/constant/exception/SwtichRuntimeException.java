package com.el.constant.exception;

/**
 * 开关运行异常 <br/>
 * since 2020/8/27
 *
 * @author eddie.lys
 */
public class SwtichRuntimeException extends RuntimeException{

    public SwtichRuntimeException() {
        super();
    }

    public SwtichRuntimeException(String message) {
        super(message);
    }

    public SwtichRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
