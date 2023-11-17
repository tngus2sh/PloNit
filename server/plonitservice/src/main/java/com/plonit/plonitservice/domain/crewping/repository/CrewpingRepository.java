package com.plonit.plonitservice.domain.crewping.repository;

import com.plonit.plonitservice.domain.crewping.Crewping;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CrewpingRepository extends JpaRepository<Crewping, Long> {

    @EntityGraph(attributePaths = "crew")
    Optional<Crewping> findById(Long crewpingId);
}
