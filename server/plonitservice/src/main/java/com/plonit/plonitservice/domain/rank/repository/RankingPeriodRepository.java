package com.plonit.plonitservice.domain.rank.repository;

import com.plonit.plonitservice.domain.rank.RankingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RankingPeriodRepository extends JpaRepository<RankingPeriod, Long> {
    
}
