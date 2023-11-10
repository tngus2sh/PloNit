package com.plonit.ploggingservice.domain.plogging.repository;

import com.plonit.ploggingservice.domain.plogging.LatLong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LatLongRepository extends JpaRepository<LatLong, Long> {
    
    
    
}
