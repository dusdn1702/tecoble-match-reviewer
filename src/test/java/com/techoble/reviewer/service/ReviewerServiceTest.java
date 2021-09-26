package com.techoble.reviewer.service;

import com.techoble.reviewer.domain.Crew;
import com.techoble.reviewer.domain.CrewRepository;
import com.techoble.reviewer.exception.DuplicateCrewException;
import com.techoble.reviewer.exception.IllegalPartException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.techoble.reviewer.domain.Part.BACKEND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ReviewerServiceTest {

    private static final String SALLY = "샐리";
    private static final String DANI = "다니";
    private static final String YB = "와이비";
    private static final String WILDER = "와일더";

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private CrewRepository crews;

    @BeforeEach
    void setUp() {
        crews.deleteAll();
    }

    @Test
    void add() {
        Crew crew = new Crew(SALLY, BACKEND);
        reviewerService.add(SALLY, "BACKEND");

        assertThat(crews.findAll()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(List.of(crew));
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

//    @Test
//    void findCrews() {
//        assertThat(crews.size()).isZero();
//        crews.add(SALLY);
//
//        CrewsDto actual = reviewerService.findCrews();
//        CrewsDto expected = new CrewsDto(List.of(SALLY));
//
//        assertThat(actual)
//                .usingRecursiveComparison()
//                .isEqualTo(expected);
//    }
//
//    @Test
//    void shuffleException() {
//        // given
//        reviewerService.add(SALLY);
//        reviewerService.add(DANI);
//
//        // when, then
//        assertThatThrownBy(() -> reviewerService.shuffle())
//                .isInstanceOf(CannotMatchException.class)
//                .hasMessage("리뷰어 매칭을 위한 최소 인원은 3명입니다.");
//    }
//
//    @Test
//    void findReviewers() {
//        // given
//        reviewerService.add(SALLY);
//        reviewerService.add(DANI);
//        reviewerService.add(YB);
//        reviewerService.add(WILDER);
//
//        ReviewersDto expected = new ReviewersDto(
//                List.of("샐리 다니 와이비", "다니 와이비 와일더", "와이비 와일더 샐리", "와일더 샐리 다니")
//        );
//
//        // when
//        ReviewersDto actual = reviewerService.findReviewers();
//
//        // then
//        assertThat(actual)
//                .usingRecursiveComparison()
//                .isEqualTo(expected);
//    }
}
