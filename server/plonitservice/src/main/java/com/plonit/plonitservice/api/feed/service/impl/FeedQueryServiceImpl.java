package com.plonit.plonitservice.api.feed.service.impl;

import com.plonit.plonitservice.api.feed.controller.response.FindFeedRes;
import com.plonit.plonitservice.api.feed.service.FeedQueryService;
import com.plonit.plonitservice.common.util.RedisUtils;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewQueryRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewRepository;
import com.plonit.plonitservice.domain.feed.repository.FeedPictureRepository;
import com.plonit.plonitservice.domain.feed.repository.FeedQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.END;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FeedQueryServiceImpl implements FeedQueryService {

    private final CrewRepository crewRepository;
    private final CrewQueryRepository crewQueryRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    private final FeedPictureRepository feedPictureRepository;
    private final FeedQueryRepository feedQueryRepository;
    private final RedisUtils redisUtils;

    public List<FindFeedRes> findFeeds(Long memberKey, Long crewId) { // 피드 조회
        log.info(logCurrent(getClassName(), getMethodName(), START));

        List<FindFeedRes> findFeedRes = feedQueryRepository.findFeedsWithPictureAndComment(memberKey, crewId);

        for (FindFeedRes feed: findFeedRes) {
            String countKey = "FEED_COUNT:" + feed.getId();
            String value = redisUtils.getRedisValue(countKey);

            if(value != null) {
                Integer likeCount = Integer.valueOf(value);
                feed.setLikeCount(likeCount);
            }

            String feedKey = "FEED_LIKE:" + feed.getId();
            Boolean isValue = redisUtils.isRedisSetValue(feedKey, String.valueOf(memberKey));

            if(isValue) {
                feed.setIsLike(true);
            }
        }

        if (findFeedRes.isEmpty())
            return Collections.emptyList();

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return findFeedRes;
    }
}