package com.meta.advice.exception;

public class CUserNotExistException extends RuntimeException {
    public CUserNotExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public CUserNotExistException(String msg) {
        super(msg);
    }

    public CUserNotExistException() {
        super();
    }
}

