package com.plonit.plonitservice.domain.badge.repository;

import com.plonit.plonitservice.common.enums.BadgeCode;
import com.plonit.plonitservice.domain.badge.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    Optional<Badge> findById(Long badgeId);

    Optional<Badge> findByCode(BadgeCode code);

}
