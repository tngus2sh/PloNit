package com.plonit.plonitservice.domain.feed.repository;

import com.plonit.plonitservice.domain.feed.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);
    Optional<Comment> findByIdAndMember_Id(Long id, Long memberKey);

}