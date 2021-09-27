package com.techoble.reviewer.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {

    boolean existsByName(String name);

    List<Crew> findAllByPart(Part part);

    void deleteByName(String name);
}
