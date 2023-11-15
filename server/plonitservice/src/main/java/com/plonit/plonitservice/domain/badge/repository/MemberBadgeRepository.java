package com.plonit.plonitservice.domain.badge.repository;

import com.plonit.plonitservice.domain.badge.MemberBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {

    @Query("SELECT m FROM MemberBadge m WHERE m.id =: id")
    Optional<Long> existById(Long id);
}
