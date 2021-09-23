package com.techoble.reviewer.domain;

import java.util.List;

public class CrewsDto {
    private final List<String> crews;

    public CrewsDto(List<String> crews) {
        this.crews = crews;
    }

    public List<String> getCrews() {
        return crews;
    }
}
