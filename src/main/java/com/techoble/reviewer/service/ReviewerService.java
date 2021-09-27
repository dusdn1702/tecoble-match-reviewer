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
import com.techoble.reviewer.exception.IllegalPartException;
import java.util.List;
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
