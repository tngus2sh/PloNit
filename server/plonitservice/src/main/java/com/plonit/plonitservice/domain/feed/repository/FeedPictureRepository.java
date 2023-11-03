package com.plonit.plonitservice.domain.feed.repository;

import com.plonit.plonitservice.domain.feed.FeedPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FeedPictureRepository extends JpaRepository<FeedPicture, Long> {
    Optional<FeedPicture> findById(Long id);
}