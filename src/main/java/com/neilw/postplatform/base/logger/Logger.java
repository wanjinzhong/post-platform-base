package com.neilw.postplatform.base.logger;

public interface Logger {
    void debug(String message);
    void info(String message);
    void warn(String message);
    void error(String message);
    void error(Throwable e);
    void pure(String message);
    void flush();
}
