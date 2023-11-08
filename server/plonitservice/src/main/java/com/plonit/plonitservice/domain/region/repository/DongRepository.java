package com.plonit.plonitservice.domain.region.repository;

import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.region.Dong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DongRepository extends JpaRepository<Dong, Long> {
    Optional<Dong> findById(Long id);

    Optional<Dong> findByCode(Long code);
}