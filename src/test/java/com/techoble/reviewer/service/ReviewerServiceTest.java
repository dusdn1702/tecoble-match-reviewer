package com.techoble.reviewer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.techoble.reviewer.domain.Crew;
import com.techoble.reviewer.domain.CrewRepository;
import com.techoble.reviewer.domain.Part;
import com.techoble.reviewer.dto.CrewsDto;
import com.techoble.reviewer.dto.ReviewersDto;
import com.techoble.reviewer.exception.DuplicateCrewException;
import com.techoble.reviewer.exception.IllegalPartException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ReviewerServiceTest {

    private static final String SALLY = "샐리";
    private static final String DANI = "다니";
    private static final String YB = "와이비";
    private static final String WILDER = "와일더";
    private static final String MICKEY = "미키";
    private static final String KYLE = "카일";
    private static final String JUMO = "주모";

    private static final String BACKEND = "BACKEND";
    private static final String FRONTEND = "FRONTEND";

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
            .hasMessage("이미 등록되어 있습니다.");
    }

    @Test
    void addCrewException_IllegalPart() {
        assertThatThrownBy(() -> reviewerService.saveCrew(SALLY, "BACK"))
            .isInstanceOf(IllegalPartException.class)
            .hasMessage("백엔드 또는 프론트엔드만 가능합니다.");
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
    void findReviewers() {
        // given
        reviewerService.saveCrew(SALLY, BACKEND);
        reviewerService.saveCrew(DANI, BACKEND);
        reviewerService.saveCrew(YB, BACKEND);
        reviewerService.saveCrew(WILDER, BACKEND);

        reviewerService.saveCrew(MICKEY, FRONTEND);
        reviewerService.saveCrew(KYLE, FRONTEND);
        reviewerService.saveCrew(JUMO, FRONTEND);

        // when
        ReviewersDto actual = reviewerService.findReviewers();

        // then
        assertThat(actual.getBackendReviewers()).hasSize(4);
        assertThat(actual.getFrontendReviewers()).hasSize(3);
    }

    @Test
    void deleteAll() {
        reviewerService.saveCrew(SALLY, BACKEND);

        reviewerService.deleteAllCrews();

        assertThat(reviewerService.findCrews().getBackendCrews()).isEmpty();
        assertThat(reviewerService.findCrews().getFrontendCrews()).isEmpty();
    }

    @Test
    void delete() {
        reviewerService.saveCrew(SALLY, BACKEND);

        reviewerService.deleteCrew(SALLY);

        assertThat(reviewerService.findCrews().getBackendCrews()).isEmpty();
    }
}
