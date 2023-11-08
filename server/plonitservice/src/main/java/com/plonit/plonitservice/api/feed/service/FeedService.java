package com.plonit.plonitservice.api.feed.service;

import com.plonit.plonitservice.api.feed.service.dto.SaveCommentDto;
import com.plonit.plonitservice.api.feed.service.dto.SaveFeedDto;

public interface FeedService {
    void saveFeed(SaveFeedDto saveFeedDto);
    void deleteFeed(Long memberKey, Long feedId);
    void saveComment(SaveCommentDto saveCommentDto);
    void deleteComment(Long memberKey, Long commentId);
    boolean saveFeedLike(Long feedId);
}
