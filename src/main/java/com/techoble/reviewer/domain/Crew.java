package com.techoble.reviewer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Crew {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Part part;

    protected Crew() {
    }

    public Crew(String name, Part part) {
        this.name = name;
        this.part = part;
    }

    public String getName() {
        return name;
    }
}
