package com.neilw.postplatform.base.logger;

public class DefaultLogger implements Logger {

    @Override
    public void debug(String message) {
        System.out.println(message);
    }

    @Override
    public void info(String message) {
        System.out.println(message);
    }

    @Override
    public void warn(String message) {
        System.out.println(message);
    }

    @Override
    public void error(String message) {
        System.out.println(message);
    }

    @Override
    public void error(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void flush() {

    }
}
