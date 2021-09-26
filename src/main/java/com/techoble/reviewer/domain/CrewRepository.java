package com.techoble.reviewer.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {
    boolean existsByName(String name);

    List<Crew> findAllByPart(Part part);
}
