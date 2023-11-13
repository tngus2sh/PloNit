package com.plonit.ploggingservice.domain.plogging.repository;

import com.plonit.ploggingservice.domain.plogging.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
