package com.techoble.reviewer.exception;

public class IllegalPartException extends ReviewerException {

    private static final String MESSAGE = "불가능한 파트입니다. BACKEND / FRONTEND로 입력해주세요.";

    public IllegalPartException() {
        super(MESSAGE);
    }
}
