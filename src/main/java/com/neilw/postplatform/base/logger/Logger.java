package com.neilw.postplatform.base.logger;

import com.neilw.postplatform.base.exception.BizException;

public interface Logger {
    void debug(String message);
    void info(String message);
    void warn(String message);
    void error(String message);
    void error(BizException e);
    void error(Throwable e);
    void pure(String message);
    void flush();
}
