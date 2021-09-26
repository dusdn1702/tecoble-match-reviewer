package com.techoble.reviewer.dto;

import com.techoble.reviewer.domain.Crew;

import java.util.List;

public class CrewsDto {
    private List<Crew> backend;
    private List<Crew> frontend;

    public CrewsDto() {
    }

    public CrewsDto(List<Crew> backend, List<Crew> frontend) {
        this.backend = backend;
        this.frontend = frontend;
    }

    public List<Crew> getBackend() {
        return backend;
    }

    public List<Crew> getFrontend() {
        return frontend;
    }
}
