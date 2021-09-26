package com.techoble.reviewer.service;

import com.techoble.reviewer.domain.Crew;
import com.techoble.reviewer.domain.CrewRepository;
import com.techoble.reviewer.domain.Part;
import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.dto.ReviewersDto;
import com.techoble.reviewer.exception.CannotMatchException;
import com.techoble.reviewer.exception.DuplicateCrewException;
import com.techoble.reviewer.exception.IllegalPartException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ReviewerServiceTest {
    private static final String SALLY = "샐리";
    private static final String DANI = "다니";
    private static final String YB = "와이비";
    private static final String WILDER = "와일더";
    private static final String BACKEND = "BACKEND";

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private CrewRepository crews;
    private static final Crew CREW_SALLY = new Crew(SALLY, Part.BACKEND);

    @BeforeEach
    void setUp() {
        crews.deleteAll();
    }

    @Test
    void add() {
        reviewerService.add(SALLY, BACKEND);

        assertThat(crews.findAll()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(List.of(CREW_SALLY));
    }

    @Test
    @DisplayName("존재하는 이름 입력할 때")
    void addException() {
        reviewerService.add(SALLY, "BACKEND");
        assertThatThrownBy(() -> reviewerService.add(SALLY, "BACKEND"))
                .isInstanceOf(DuplicateCrewException.class)
                .hasMessage("이미 존재하는 크루입니다.");
    }

    @Test
    @DisplayName("불가능한 파트 입력할 때")
    void addExceptionImpossiblePart() {
        assertThatThrownBy(() -> reviewerService.add(SALLY, "BACK"))
                .isInstanceOf(IllegalPartException.class);
    }

    @Test
    void findCrews() {
        assertThat(reviewerService.findCrews()).usingRecursiveComparison()
                .isEqualTo(new CrewsDto(Collections.emptyList(), Collections.emptyList()));


        reviewerService.add(SALLY, "BACKEND");

        CrewsDto actual = reviewerService.findCrews();

        assertThat(actual.getBackend().size()).isEqualTo(1);
        assertThat(actual.getFrontend().size()).isZero();
    }

    @Test
    void shuffleException() {
        // given
        reviewerService.add(SALLY, BACKEND);
        reviewerService.add(DANI, BACKEND);

        // when, then
        assertThatThrownBy(() -> reviewerService.findReviewers())
                .isInstanceOf(CannotMatchException.class)
                .hasMessage("리뷰어 매칭을 위한 최소 인원은 3명입니다.");
    }

    @Test
    void findReviewers() {
        // given
        List<Crew> crews = List.of(
                new Crew(SALLY, Part.BACKEND),
                new Crew(DANI, Part.BACKEND),
                new Crew(YB, Part.BACKEND),
                new Crew(WILDER, Part.BACKEND)
        );

        reviewerService.add(SALLY, BACKEND);
        reviewerService.add(DANI, BACKEND);
        reviewerService.add(YB, BACKEND);
        reviewerService.add(WILDER, BACKEND);

        ReviewersDto expected = new ReviewersDto(
                List.of("백엔드 매칭 결과",
                        "샐리리뷰어: 다니, 와이비",
                        "다니리뷰어: 와이비, 와일더",
                        "와이비리뷰어: 와일더, 샐리",
                        "와일더리뷰어: 샐리, 다니"),
                List.of("프론트엔드 매칭 결과")
        );

        // when
        ReviewersDto actual = reviewerService.matchReviewers(crews, Collections.emptyList());

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
