package com.plonit.plonitservice.domain.rank.repository;

import com.plonit.plonitservice.domain.rank.CrewRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewRankingRepository extends JpaRepository<CrewRanking, Long> {
}
