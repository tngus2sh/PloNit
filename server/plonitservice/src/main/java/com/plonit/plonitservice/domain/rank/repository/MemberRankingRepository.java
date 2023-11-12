package com.plonit.plonitservice.domain.rank.repository;

import com.plonit.plonitservice.domain.rank.MemberRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRankingRepository extends JpaRepository<MemberRanking, Long> {
}
