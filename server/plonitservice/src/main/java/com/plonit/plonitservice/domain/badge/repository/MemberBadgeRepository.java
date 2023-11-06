package com.plonit.plonitservice.domain.badge.repository;

import com.plonit.plonitservice.domain.badge.MemberBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {
}
