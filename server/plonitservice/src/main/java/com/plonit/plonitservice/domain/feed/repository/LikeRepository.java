package com.plonit.plonitservice.domain.feed.repository;

import com.plonit.plonitservice.domain.feed.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
