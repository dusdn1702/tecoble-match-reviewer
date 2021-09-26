package com.techoble.reviewer.service;

import com.techoble.reviewer.domain.Crew;
import com.techoble.reviewer.domain.CrewRepository;
import com.techoble.reviewer.domain.Part;
import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.dto.ReviewersDto;
import com.techoble.reviewer.exception.CannotMatchException;
import com.techoble.reviewer.exception.DuplicateCrewException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.techoble.reviewer.exception.IllegalPartException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(crews.existsByName(name)) {
            throw new DuplicateCrewException();
        }
    }

    public CrewsDto findCrews() {
        List<Crew> backend = crews.findAllByPart(BACKEND);
        List<Crew> frontend = crews.findAllByPart(FRONTEND);
        return new CrewsDto(backend, frontend);
    }

//    public void shuffle() {
//        validateSize();
//        Collections.shuffle(crews);
//    }
//
//    private void validateSize() {
//        if (crews.size() < 3) {
//            throw new CannotMatchException();
//        }
//    }
//
//    public ReviewersDto findReviewers() {
//        return new ReviewersDto(match());
//    }
//
//    private List<String> match() {
//        List<String> reviewers = new ArrayList<>();
//
//        for (int i = 0; i < crews.size(); i++) {
//            if (isBeforeLast(i)) {
//                reviewers.add(crews.get(i) + " " + crews.get(i + 1) + " " + crews.get(0));
//                continue;
//            }
//            if (isLast(i)) {
//                reviewers.add(crews.get(i) + " " + crews.get(0) + " " + crews.get(1));
//                break;
//            }
//            reviewers.add(crews.get(i) + " " + crews.get(i + 1) + " " + crews.get(i + 2));
//        }
//
//        return reviewers;
//    }
//
//    private boolean isBeforeLast(int i) {
//        return i == crews.size() - 2;
//    }
//
//    private boolean isLast(int i) {
//        return i == crews.size() - 1;
//    }
}
