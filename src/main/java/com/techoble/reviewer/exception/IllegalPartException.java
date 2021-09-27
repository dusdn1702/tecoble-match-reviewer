package com.techoble.reviewer.exception;

public class IllegalPartException extends ReviewerException {

    private static final String MESSAGE = "백엔드 또는 프론트엔드만 가능합니다.";

    public IllegalPartException() {
        this(MESSAGE);
    }

    private IllegalPartException(String message) {
        super(message);
    }
}
