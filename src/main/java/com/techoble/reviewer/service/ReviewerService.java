package com.techoble.reviewer.service;

import com.techoble.reviewer.dto.CrewsDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReviewerService {

    public static List<String> crews = new ArrayList<>();

    public void add(final String name) {
        if (crews.contains(name)) {
            throw new IllegalArgumentException("이미 존재하는 크루입니다.");
        }
        crews.add(name);
    }

    public CrewsDto findCrews() {
        return new CrewsDto(crews);
    }
}
