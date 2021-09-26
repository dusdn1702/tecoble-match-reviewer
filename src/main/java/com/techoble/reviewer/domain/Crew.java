package com.techoble.reviewer.domain;

import javax.persistence.*;

@Entity
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Part part;

    public Crew(String name, Part part) {
        this.name = name;
        this.part = part;
    }

    public Crew() {

    }

    public String getName() {
        return name;
    }
}
