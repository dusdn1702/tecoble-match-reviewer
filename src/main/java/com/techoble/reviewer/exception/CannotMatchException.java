package com.techoble.reviewer.exception;

public class CannotMatchException extends ReviewerException {

    private static final String MESSAGE = "매칭을 위한 최소 인원은 3명입니다.";

    public CannotMatchException() {
        this(MESSAGE);
    }

    private CannotMatchException(String message) {
        super(message);
    }
}
