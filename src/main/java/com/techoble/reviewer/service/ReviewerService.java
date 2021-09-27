package com.techoble.reviewer.service;

import static com.techoble.reviewer.domain.Part.BACKEND;
import static com.techoble.reviewer.domain.Part.FRONTEND;

import com.techoble.reviewer.domain.Crew;
import com.techoble.reviewer.domain.CrewRepository;
import com.techoble.reviewer.domain.Crews;
import com.techoble.reviewer.domain.Part;
import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.dto.ReviewersDto;
import com.techoble.reviewer.exception.DuplicateCrewException;
import com.techoble.reviewer.exception.IllegalNameException;
import com.techoble.reviewer.exception.IllegalPartException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReviewerService {

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
        validate(name, part);

        Part findPart = Part.valueOf(part);
        Crew crew = new Crew(name, findPart);

        crewRepository.save(crew);
    }

    @Transactional
    public void deleteAllCrews() {
        crewRepository.deleteAll();
    }

    @Transactional
    public void deleteCrew(String name) {
        crewRepository.deleteByName(name);
    }

    private void validate(String name, String part) {
        validateName(name);
        validatePart(part);
    }

    private void validateName(String name) {
        if (crewRepository.existsByName(name)) {
            throw new DuplicateCrewException();
        }
        if (name.trim().isBlank()){
            throw new IllegalNameException();
        }
    }

    private void validatePart(String part) {
        Optional<Part> findPart = Arrays.stream(Part.values())
            .filter(value -> value.name().equals(part))
            .findAny();

        if (findPart.isEmpty()) {
            throw new IllegalPartException();
        }
    }

    public ReviewersDto findReviewers() {
        Crews backendCrews = new Crews(crewRepository.findAllByPart(BACKEND));
        backendCrews.shuffle();

        Crews frontendCrews = new Crews(crewRepository.findAllByPart(FRONTEND));
        frontendCrews.shuffle();

        return match(backendCrews, frontendCrews);
    }

    private ReviewersDto match(Crews backendCrews, Crews frontendCrews) {
        List<String> backendReviewers = backendCrews.match();
        List<String> frontendReviewers = frontendCrews.match();

        return new ReviewersDto(backendReviewers, frontendReviewers);
    }
}
