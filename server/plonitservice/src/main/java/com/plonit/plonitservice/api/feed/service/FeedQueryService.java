package com.plonit.plonitservice.api.feed.service;

import com.plonit.plonitservice.api.feed.controller.response.FindFeedRes;

import java.util.List;

public interface FeedQueryService {
    List<FindFeedRes> findFeeds(Long memberKey, Long crewId);
}