package com.techoble.reviewer.exception;

public class DuplicateCrewException extends ReviewerException {

    private static final String MESSAGE = "이미 존재하는 크루입니다.";

    public DuplicateCrewException() {
        this(MESSAGE);
    }

    private DuplicateCrewException(String message) {
        super(message);
    }
}
