package com.plonit.ploggingservice.domain.plogging.repository;

import com.plonit.ploggingservice.domain.plogging.Plogging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PloggingRepository extends JpaRepository<Plogging, Long> {
    
    Optional<Plogging> findById(Long id);
    
    @Query("SELECT p.id FROM Plogging p WHERE p.id=:id")
    Optional<Long> existById(Long id);
    
}
