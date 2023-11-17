package com.plonit.plonitservice.api.feed.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.plonit.plonitservice.api.feed.service.dto.SaveCommentDto;
import com.plonit.plonitservice.api.feed.service.dto.SaveFeedDto;
import com.plonit.plonitservice.domain.feed.Like;

public interface FeedService {
    void saveFeed(SaveFeedDto saveFeedDto);
    void deleteFeed(Long memberKey, Long feedId);
    void saveComment(SaveCommentDto saveCommentDto);
    void deleteComment(Long memberKey, Long commentId);
    boolean saveFeedLike(Long feedId);
    void likeFeed(Long memberId, Long feedId);
    void dislikeFeed(Long memberId, Long feedId, Boolean isRedis, Like like);
}
