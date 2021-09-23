package com.techoble.reviewer.service;

import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.dto.ReviewersDto;
import com.techoble.reviewer.exception.CannotMatchException;
import com.techoble.reviewer.exception.DuplicateCrewException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReviewerService {

    protected static List<String> crews = new ArrayList<>();

    public void add(final String name) {
        validateContains(name);
        crews.add(name);
    }

    private void validateContains(String name) {
        if (crews.contains(name)) {
            throw new DuplicateCrewException();
        }
    }

    public CrewsDto findCrews() {
        return new CrewsDto(crews);
    }

    public void match() {
        validateSize();
        shuffle();
    }

    private void validateSize() {
        if (crews.size() < 3) {
            throw new CannotMatchException();
        }
    }

    private void shuffle() {
        Collections.shuffle(crews);
    }

    public ReviewersDto findReviewers() {
        return new ReviewersDto(matchReviewers());
    }

    private List<String> matchReviewers() {
        List<String> reviewers = new ArrayList<>();

        for (int i = 0; i < crews.size(); i++) {
            if (isBeforeLast(i)) {
                reviewers.add(crews.get(i) + " " + crews.get(i + 1) + " " + crews.get(0));
                continue;
            }
            if (isLast(i)) {
                reviewers.add(crews.get(i) + " " + crews.get(0) + " " + crews.get(1));
                break;
            }
            reviewers.add(crews.get(i) + " " + crews.get(i + 1) + " " + crews.get(i + 2));
        }

        return reviewers;
    }

    private boolean isBeforeLast(int i) {
        return i == crews.size() - 2;
    }

    private boolean isLast(int i) {
        return i == crews.size() - 1;
    }
}
