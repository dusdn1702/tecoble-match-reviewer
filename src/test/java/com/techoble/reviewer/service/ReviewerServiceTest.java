package com.techoble.reviewer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.techoble.reviewer.domain.Crew;
import com.techoble.reviewer.domain.CrewRepository;
import com.techoble.reviewer.domain.Part;
import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.dto.ReviewersDto;
import com.techoble.reviewer.exception.CannotMatchException;
import com.techoble.reviewer.exception.DuplicateCrewException;
import com.techoble.reviewer.exception.IllegalPartException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReviewerServiceTest {

    private static final String SALLY = "샐리";
    private static final String DANI = "다니";
    private static final String YB = "와이비";
    private static final String WILDER = "와일더";
    private static final String BACKEND = "BACKEND";
    private static final Crew SALLY_BACKEND = new Crew(SALLY, Part.BACKEND);

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private CrewRepository crewRepository;

    @BeforeEach
    void setUp() {
        crewRepository.deleteAll();
    }

    @Test
    void addCrew() {
        reviewerService.saveCrew(SALLY, BACKEND);

        assertThat(crewRepository.findAll())
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(List.of(SALLY_BACKEND));
    }

    @Test
    void addCrewException_DuplicateCrew() {
        reviewerService.saveCrew(SALLY, "BACKEND");

        assertThatThrownBy(() -> reviewerService.saveCrew(SALLY, "BACKEND"))
            .isInstanceOf(DuplicateCrewException.class)
            .hasMessage("이미 존재하는 크루입니다.");
    }

    @Test
    void addCrewException_IllegalPart() {
        assertThatThrownBy(() -> reviewerService.saveCrew(SALLY, "BACK"))
            .isInstanceOf(IllegalPartException.class)
            .hasMessage("불가능한 파트입니다. BACKEND / FRONTEND로 입력해주세요.");
    }

    @Test
    void findCrews() {
        assertThat(reviewerService.findCrews())
            .usingRecursiveComparison()
            .isEqualTo(new CrewsDto(Collections.emptyList(), Collections.emptyList()));

        reviewerService.saveCrew(SALLY, "BACKEND");

        CrewsDto actual = reviewerService.findCrews();

        assertThat(actual.getBackendCrews()).hasSize(1);
        assertThat(actual.getFrontendCrews()).isEmpty();
    }

    @Test
    void shuffleException() {
        // given
        reviewerService.saveCrew(SALLY, BACKEND);
        reviewerService.saveCrew(DANI, BACKEND);

        // when, then
        assertThatThrownBy(() -> reviewerService.findReviewers())
            .isInstanceOf(CannotMatchException.class)
            .hasMessage("리뷰어 매칭을 위한 최소 인원은 3명입니다.");
    }

    @Test
    void findReviewers() {
        // given
        List<Crew> backendCrews = List.of(
            new Crew(SALLY, Part.BACKEND),
            new Crew(DANI, Part.BACKEND),
            new Crew(YB, Part.BACKEND),
            new Crew(WILDER, Part.BACKEND)
        );

        reviewerService.saveCrew(SALLY, BACKEND);
        reviewerService.saveCrew(DANI, BACKEND);
        reviewerService.saveCrew(YB, BACKEND);
        reviewerService.saveCrew(WILDER, BACKEND);

        ReviewersDto expected = new ReviewersDto(
            List.of(
                "🪐 백엔드<br/>",
                "샐리 리뷰어 - 다니, 와이비",
                "다니 리뷰어 - 와이비, 와일더",
                "와이비 리뷰어 - 와일더, 샐리",
                "와일더 리뷰어 - 샐리, 다니"
            ),
            List.of(
                "🪐 프론트엔드<br/>"
            )
        );

        // when
        ReviewersDto actual = reviewerService.matchReviewers(backendCrews, Collections.emptyList());

        // then
        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}
