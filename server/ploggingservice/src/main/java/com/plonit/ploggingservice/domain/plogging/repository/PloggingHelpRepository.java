package com.plonit.ploggingservice.domain.plogging.repository;

import com.plonit.ploggingservice.domain.plogging.PloggingHelp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PloggingHelpRepository extends JpaRepository<PloggingHelp, Long> {

    Optional<PloggingHelp> findByIdAndIsActive(Long id, Boolean isActive);

    
}
