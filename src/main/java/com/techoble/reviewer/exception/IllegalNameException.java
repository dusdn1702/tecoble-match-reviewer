package com.techoble.reviewer.exception;

public class IllegalNameException extends ReviewerException {

    private static final String MESSAGE = "최소 1글자 이상 입력해주세요.";

    public IllegalNameException() {
        this(MESSAGE);
    }

    private IllegalNameException(String message) {
        super(message);
    }
}
