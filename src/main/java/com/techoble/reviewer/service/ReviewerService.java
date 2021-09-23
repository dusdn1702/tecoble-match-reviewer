package com.techoble.reviewer.service;

import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.exception.DuplicateCrewException;
import java.util.ArrayList;
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
}
