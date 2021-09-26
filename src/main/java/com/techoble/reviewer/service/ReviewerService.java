package com.techoble.reviewer.service;

import com.techoble.reviewer.domain.Crew;
import com.techoble.reviewer.domain.CrewRepository;
import com.techoble.reviewer.domain.Part;
import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.dto.ReviewersDto;
import com.techoble.reviewer.exception.CannotMatchException;
import com.techoble.reviewer.exception.DuplicateCrewException;
import com.techoble.reviewer.exception.IllegalPartException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.techoble.reviewer.domain.Part.BACKEND;
import static com.techoble.reviewer.domain.Part.FRONTEND;

@Service
@Transactional
public class ReviewerService {
    private final CrewRepository crews;

    public ReviewerService(CrewRepository crews) {
        this.crews = crews;
    }

    public void add(final String name, final String inputPart) {
        validateContains(name);
        Part part = matchPart(inputPart);
        Crew crew = new Crew(name, part);

        crews.save(crew);
    }

    private Part matchPart(String inputPart) {
        try {
            return Part.valueOf(inputPart);
        } catch (IllegalArgumentException e) {
            throw new IllegalPartException();
        }
    }

    private void validateContains(String name) {
        if (crews.existsByName(name)) {
            throw new DuplicateCrewException();
        }
    }

    public CrewsDto findCrews() {
        List<Crew> backend = crews.findAllByPart(BACKEND);
        List<Crew> frontend = crews.findAllByPart(FRONTEND);
        return new CrewsDto(backend, frontend);
    }

    public ReviewersDto findReviewers() {
        List<Crew> backend = crews.findAllByPart(BACKEND);
        validateSize(backend);
        Collections.shuffle(backend);

        List<Crew> frontend = crews.findAllByPart(FRONTEND);
        validateSize(frontend);
        Collections.shuffle(frontend);

        return matchReviewers(backend, frontend);
    }

    private void validateSize(List<Crew> part) {
        if (part.size() < 3) {
            throw new CannotMatchException();
        }
    }

    public ReviewersDto matchReviewers(List<Crew> backend, List<Crew> frontend) {
        List<String> backendReviewers = new ArrayList<>();
        backendReviewers.add("백엔드 매칭 결과");
        matchPart(backend, backendReviewers);

        List<String> frontendReviewers = new ArrayList<>();
        frontendReviewers.add("프론트엔드 매칭 결과");
        matchPart(frontend, frontendReviewers);

        return new ReviewersDto(backendReviewers, frontendReviewers);
    }

    private void matchPart(List<Crew> partCrews, List<String> reviewers) {
        for (int i = 0; i < partCrews.size(); i++) {
            if (isBeforeLast(i, partCrews.size())) {
                reviewers.add(partCrews.get(i).getName() + "리뷰어: " + partCrews.get(i + 1).getName() + ", " + partCrews.get(0).getName());
                continue;
            }
            if (isLast(i, partCrews.size())) {
                reviewers.add(partCrews.get(i).getName() + "리뷰어: " + partCrews.get(0).getName() + ", " + partCrews.get(1).getName());
                break;
            }
            reviewers.add(partCrews.get(i).getName() + "리뷰어: " + partCrews.get(i + 1).getName() + ", " + partCrews.get(i + 2).getName());
        }
    }

    private boolean isBeforeLast(int i, int size) {
        return i == size - 2;
    }

    private boolean isLast(int i, int size) {
        return i == size - 1;
    }
}
