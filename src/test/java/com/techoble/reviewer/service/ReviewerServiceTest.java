package com.techoble.reviewer.service;

import static com.techoble.reviewer.service.ReviewerService.crews;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.dto.ReviewersDto;
import com.techoble.reviewer.exception.CannotMatchException;
import com.techoble.reviewer.exception.DuplicateCrewException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReviewerServiceTest {

    private static final String SALLY = "샐리";
    private static final String DANI = "다니";
    private static final String YB = "와이비";
    private static final String WILDER = "와일더";

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
            .isInstanceOf(DuplicateCrewException.class)
            .hasMessage("이미 존재하는 크루입니다.");
    }

    @Test
    void findCrews() {
        assertThat(crews.size()).isZero();
        crews.add(SALLY);

        CrewsDto actual = reviewerService.findCrews();
        CrewsDto expected = new CrewsDto(List.of(SALLY));

        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    void shuffleException() {
        // given
        reviewerService.add(SALLY);
        reviewerService.add(DANI);

        // when, then
        assertThatThrownBy(() -> reviewerService.shuffle())
            .isInstanceOf(CannotMatchException.class)
            .hasMessage("리뷰어 매칭을 위한 최소 인원은 3명입니다.");
    }

    @Test
    void findReviewers() {
        // given
        reviewerService.add(SALLY);
        reviewerService.add(DANI);
        reviewerService.add(YB);
        reviewerService.add(WILDER);

        ReviewersDto expected = new ReviewersDto(
            List.of("샐리 다니 와이비", "다니 와이비 와일더", "와이비 와일더 샐리", "와일더 샐리 다니")
        );

        // when
        ReviewersDto actual = reviewerService.findReviewers();

        // then
        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}
