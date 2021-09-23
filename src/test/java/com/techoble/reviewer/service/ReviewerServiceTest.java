package com.techoble.reviewer.service;

import com.techoble.reviewer.dto.CrewsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.techoble.reviewer.service.ReviewerService.crews;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ReviewerServiceTest {
    private static final String SALLY = "sally";

    @Autowired
    private ReviewerService reviewerService;

    @BeforeEach
    void setUp() {
        crews = new ArrayList<>();
    }

    @Test
    void add() {
        reviewerService.add(SALLY);
        assertThat(crews).containsExactly(SALLY);
    }

    @Test
    void addException() {
        reviewerService.add(SALLY);
        assertThatThrownBy(() -> reviewerService.add(SALLY))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void findCrews() {
        assertThat(crews.size()).isZero();
        crews.add(SALLY);

        CrewsDto crewsDto = new CrewsDto(List.of(SALLY));
        assertThat(reviewerService.findCrews()).usingRecursiveComparison()
                .isEqualTo(crewsDto);
    }
}
