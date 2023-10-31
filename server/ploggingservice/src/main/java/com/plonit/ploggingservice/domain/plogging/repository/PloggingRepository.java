package com.plonit.ploggingservice.domain.plogging.repository;

import com.plonit.ploggingservice.domain.plogging.Plogging;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PloggingRepository extends JpaRepository<Plogging, Long> {
    
    Optional<Plogging> findByPloggingId(Long ploggingId);
    
}
