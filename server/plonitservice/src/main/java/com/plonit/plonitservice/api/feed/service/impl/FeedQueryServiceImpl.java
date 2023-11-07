package com.plonit.plonitservice.api.feed.service.impl;

import com.plonit.plonitservice.api.feed.service.dto.FeedPictureDto;
import com.plonit.plonitservice.api.feed.controller.response.FindFeedRes;
import com.plonit.plonitservice.api.feed.service.FeedQueryService;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewQueryRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewRepository;
import com.plonit.plonitservice.domain.feed.Feed;
import com.plonit.plonitservice.domain.feed.repository.FeedPictureRepository;
import com.plonit.plonitservice.domain.feed.repository.FeedQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    public List<FindFeedRes> findFeeds(Long memberKey) { // 피드 조회
        log.info(logCurrent(getClassName(), getMethodName(), START));

        List<FindFeedRes> findFeedRes = feedQueryRepository.findFeedsWithPictureAndComment(memberKey);

        if (findFeedRes.isEmpty())
            return Collections.emptyList();

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return findFeedRes;
    }
}