package com.techoble.reviewer.dto;

import com.techoble.reviewer.domain.Crew;
import java.util.List;
import java.util.stream.Collectors;

public class CrewsDto {

    private List<String> backendCrews;
    private List<String> frontendCrews;

    private CrewsDto() {
    }

    public CrewsDto(List<String> backendCrews, List<String> frontendCrews) {
        this.backendCrews = backendCrews;
        this.frontendCrews = frontendCrews;
    }

    public static CrewsDto from(List<Crew> backendCrews, List<Crew> frontendCrews) {
        List<String> backendCrewsWithName = backendCrews.stream()
            .map(Crew::getName)
            .collect(Collectors.toList());
        List<String> frontendCrewsWithName = frontendCrews.stream()
            .map(Crew::getName)
            .collect(Collectors.toList());

        return new CrewsDto(backendCrewsWithName, frontendCrewsWithName);
    }

    public List<String> getBackendCrews() {
        return backendCrews;
    }

    public List<String> getFrontendCrews() {
        return frontendCrews;
    }
}
