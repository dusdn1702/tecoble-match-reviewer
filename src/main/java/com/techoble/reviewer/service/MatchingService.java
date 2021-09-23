package com.techoble.reviewer.service;

import com.techoble.reviewer.domain.CrewsDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchingService {
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
