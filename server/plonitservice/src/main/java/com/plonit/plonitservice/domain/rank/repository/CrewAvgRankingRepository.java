package com.plonit.plonitservice.domain.rank.repository;

import com.plonit.plonitservice.domain.rank.CrewAvgRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewAvgRankingRepository extends JpaRepository<CrewAvgRanking, Long> {
}
