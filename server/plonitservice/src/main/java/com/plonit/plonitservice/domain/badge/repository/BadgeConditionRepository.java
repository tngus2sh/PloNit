package com.plonit.plonitservice.domain.badge.repository;

import com.plonit.plonitservice.domain.badge.BadgeCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeConditionRepository extends JpaRepository<BadgeCondition, Long> {
    
    
    
}
