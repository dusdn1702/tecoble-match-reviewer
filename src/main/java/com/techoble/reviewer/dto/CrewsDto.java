package com.techoble.reviewer.dto;

import com.techoble.reviewer.domain.Crew;

import java.util.List;
import java.util.stream.Collectors;

public class CrewsDto {
    private List<String> backend;
    private List<String> frontend;

    public CrewsDto() {
    }

    public CrewsDto(List<Crew> backend, List<Crew> frontend) {
        this.backend = backend.stream().map(Crew::getName).collect(Collectors.toList());
        this.frontend = frontend.stream().map(Crew::getName).collect(Collectors.toList());
    }

    public List<String> getBackend() {
        return backend;
    }

    public List<String> getFrontend() {
        return frontend;
    }
}
