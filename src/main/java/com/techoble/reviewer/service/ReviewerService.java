package com.techoble.reviewer.service;

import static com.techoble.reviewer.domain.Part.BACKEND;
import static com.techoble.reviewer.domain.Part.FRONTEND;

import com.techoble.reviewer.domain.Crew;
import com.techoble.reviewer.domain.CrewRepository;
import com.techoble.reviewer.domain.Part;
import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.dto.ReviewersDto;
import com.techoble.reviewer.exception.CannotMatchException;
import com.techoble.reviewer.exception.DuplicateCrewException;
import com.techoble.reviewer.exception.IllegalPartException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReviewerService {

    private static final String REVIEWER = " 리뷰어 - ";
    private static final String COMMA = ", ";

    private final CrewRepository crewRepository;

    public ReviewerService(CrewRepository crewRepository) {
        this.crewRepository = crewRepository;
    }

    public CrewsDto findCrews() {
        List<Crew> backendCrews = crewRepository.findAllByPart(BACKEND);
        List<Crew> frontendCrews = crewRepository.findAllByPart(FRONTEND);

        return CrewsDto.from(backendCrews, frontendCrews);
    }

    @Transactional
    public void saveCrew(final String name, final String part) {
        validateContains(name);

        Part findPart = valueOf(part);
        Crew crew = new Crew(name, findPart);

        crewRepository.save(crew);
    }

    private void validateContains(String name) {
        if (crewRepository.existsByName(name)) {
            throw new DuplicateCrewException();
        }
    }

    private Part valueOf(String part) {
        try {
            return Part.valueOf(part);
        } catch (IllegalArgumentException e) {
            throw new IllegalPartException();
        }
    }

    public ReviewersDto findReviewers() {
        List<Crew> backends = crewRepository.findAllByPart(BACKEND);
        validateSize(backends);
        Collections.shuffle(backends);

        List<Crew> frontends = crewRepository.findAllByPart(FRONTEND);
        validateSize(frontends);
        Collections.shuffle(frontends);

        return matchReviewers(backends, frontends);
    }

    private void validateSize(List<Crew> parts) {
        if (parts.size() < 3) {
            throw new CannotMatchException();
        }
    }

    public ReviewersDto matchReviewers(List<Crew> backendCrews, List<Crew> frontendCrews) {
        List<String> backendReviewers = new ArrayList<>();
        backendReviewers.add("🪐 백엔드<br/>");
        match(backendCrews, backendReviewers);

        List<String> frontendReviewers = new ArrayList<>();
        frontendReviewers.add("🪐 프론트엔드<br/>");
        match(frontendCrews, frontendReviewers);

        return new ReviewersDto(backendReviewers, frontendReviewers);
    }

    private void match(List<Crew> crews, List<String> reviewers) {
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
    }

    private boolean isBeforeLast(int i, int size) {
        return i == size - 2;
    }

    private boolean isLast(int i, int size) {
        return i == size - 1;
    }
}
