package com.plonit.plonitservice.api.feed.service.impl;

import com.plonit.plonitservice.api.feed.service.FeedService;
import com.plonit.plonitservice.api.feed.service.dto.SaveFeedDto;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.domain.crew.CrewMember;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewRepository;
import com.plonit.plonitservice.domain.feed.Feed;
import com.plonit.plonitservice.domain.feed.FeedPicture;
import com.plonit.plonitservice.domain.feed.repository.FeedPictureRepository;
import com.plonit.plonitservice.domain.feed.repository.FeedRepository;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.plonit.plonitservice.common.exception.ErrorCode.*;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.END;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedServiceImpl implements FeedService {

    private final CrewRepository crewRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final FeedRepository feedRepository;
    private final FeedPictureRepository feedPictureRepository;
    private final MemberRepository memberRepository;
    private final AwsS3Uploader awsS3Uploader;

    @Transactional // 피드 생성
    public void saveFeed(SaveFeedDto saveFeedDto) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        Member member = memberRepository.findById(saveFeedDto.getMemberKey())
                .orElseThrow(() -> new CustomException(USER_BAD_REQUEST));

        CrewMember crewMember = crewMemberRepository.findCrewMemberWithCrewByFetch(saveFeedDto.getMemberKey(), saveFeedDto.getCrewId())
                .orElseThrow(() -> new CustomException(CREW_NOT_FOUND));

        Feed feed = feedRepository.save(SaveFeedDto.toEntity(member, crewMember.getCrew(), saveFeedDto));

        List<MultipartFile> multipartFiles = saveFeedDto.getFeedPictures();
        List<String> feedImageUrl = new ArrayList<>();
        for(MultipartFile item : multipartFiles) {
            if (item != null) {
                try {
                    feedImageUrl.add(awsS3Uploader.uploadFile(item, "feed/" + saveFeedDto.getCrewId() + "/" + feed.getId()));
                } catch (IOException e) {
                    throw new CustomException(S3_CONNECTED_FAIL);
                }
            }
        }

        List<FeedPicture> feedPictures = feedImageUrl.stream().map(url -> FeedPicture.builder()
                .feed(feed)
                .image(url)
                .build()).collect(Collectors.toList());
        feedPictureRepository.saveAll(feedPictures);
        log.info(logCurrent(getClassName(), getMethodName(), END));
    }
}
