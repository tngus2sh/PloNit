package com.plonit.plonitservice.domain.feed.repository;

import com.plonit.plonitservice.domain.crew.CrewMember;
import com.plonit.plonitservice.domain.feed.Comment;
import com.plonit.plonitservice.domain.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<Feed> findById(Long id);

    @Query("SELECT f FROM Feed f JOIN FETCH f.member")
    List<Feed> findFeedWithMemberByFetch();

    Optional<Feed> findByIdAndMember_Id(Long id, Long memberKey);

}