package com.techoble.reviewer.domain;

import com.techoble.reviewer.exception.CannotMatchException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Crews {

    private static final String REVIEWER = " 리뷰어 - ";
    private static final String COMMA = ", ";

    private final List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public void shuffle() {
        validate();
        Collections.shuffle(crews);
    }

    private void validate() {
        if (isLessThanMinimum()) {
            throw new CannotMatchException();
        }
    }

    private boolean isLessThanMinimum() {
        return crews.size() < 3;
    }

    public List<String> match() {
        List<String> reviewers = new ArrayList<>();
        for (int crew = 0; crew < crews.size(); crew++) {
            int firstReviewer = crew + 1;
            int secondReviewer = crew + 2;

            if (isBeforeLast(crew, crews.size())) {
                firstReviewer = crew + 1;
                secondReviewer = 0;
            }
            if (isLast(crew, crews.size())) {
                firstReviewer = 0;
                secondReviewer = 1;
            }

            reviewers.add(crews.get(crew).getName() +
                REVIEWER + crews.get(firstReviewer).getName() +
                COMMA + crews.get(secondReviewer).getName());
        }

        return reviewers;
    }

    private boolean isBeforeLast(int index, int size) {
        return index == size - 2;
    }

    private boolean isLast(int index, int size) {
        return index == size - 1;
    }
}
