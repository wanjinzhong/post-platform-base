package com.neilw.postplatform.base.exception;


/**
 * @author neilwan
 */
public class BizException extends RuntimeException{
    public BizException() {
    }

    public BizException(String format, Object... param) {
        super(String.format(format, param));
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public void throwIf(boolean shouldThrow) {
        if (shouldThrow) {
            throw this;
        }
    }
}
