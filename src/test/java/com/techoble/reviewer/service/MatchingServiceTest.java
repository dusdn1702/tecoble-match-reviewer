package com.techoble.reviewer.service;

import com.techoble.reviewer.domain.CrewsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.techoble.reviewer.service.MatchingService.crews;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MatchingServiceTest {
    private static final String SALLY = "sally";

    @Autowired
    private MatchingService matchingService;

    @BeforeEach
    void setUp() {
        crews = new ArrayList<>();
    }

    @Test
    void add() {
        matchingService.add(SALLY);
        assertThat(crews).containsExactly(SALLY);
    }

    @Test
    void addException() {
        matchingService.add(SALLY);
        assertThatThrownBy(() -> matchingService.add(SALLY))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findCrews() {
        assertThat(crews.size()).isZero();
        crews.add(SALLY);

        CrewsDto crewsDto = new CrewsDto(List.of(SALLY));
        assertThat(matchingService.findCrews()).usingRecursiveComparison()
                .isEqualTo(crewsDto);
    }
}
