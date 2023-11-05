package com.plonit.plonitservice.domain.badge.repository;

import com.plonit.plonitservice.domain.badge.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {


}
