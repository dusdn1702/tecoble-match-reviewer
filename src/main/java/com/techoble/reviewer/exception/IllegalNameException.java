package com.techoble.reviewer.exception;

public class IllegalNameException extends ReviewerException {
    private static String MESSAGE = "이름은 최소 한글자 이상 입력해야 합니다.";
    public IllegalNameException() {
        super(MESSAGE);
    }
}
