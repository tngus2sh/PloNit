package com.plonit.plonitservice.domain.region.repository;

import com.plonit.plonitservice.domain.region.Dong;
import com.plonit.plonitservice.domain.region.Gugun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface GugunRepository extends JpaRepository<Gugun, Long> {
    Optional<Gugun> findByCode(Long id);

}