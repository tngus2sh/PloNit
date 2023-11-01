package com.plonit.plonitservice.domain.crew.repository;

import com.plonit.plonitservice.domain.crew.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {
    Optional<Crew> findById(long id);
}