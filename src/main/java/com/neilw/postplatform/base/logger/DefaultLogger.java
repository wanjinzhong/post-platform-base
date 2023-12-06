package com.neilw.postplatform.base.logger;

public class DefaultLogger implements Logger {

    @Override
    public void debug(String message) {
        System.out.println("[DEBUG] " + message);
    }

    @Override
    public void info(String message) {
        System.out.println("[INFO] " + message);
    }

    @Override
    public void warn(String message) {
        System.out.println("[WARN] " + message);
    }

    @Override
    public void error(String message) {
        System.err.println("[ERROR] " + message);
    }

    @Override
    public void error(Throwable e) {
        if (e.getStackTrace() != null) {
            for (StackTraceElement element : e.getStackTrace()) {
                error(element.toString());
            }
        }
    }

    @Override
    public void pure(String message) {
        System.out.println(message);
    }

    @Override
    public void flush() {

    }
}
