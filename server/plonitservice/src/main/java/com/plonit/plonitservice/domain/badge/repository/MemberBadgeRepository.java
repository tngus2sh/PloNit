package com.plonit.plonitservice.domain.badge.repository;

import com.plonit.plonitservice.domain.badge.MemberBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {

}
