package com.plonit.plonitservice.api.feed.service.dto;

import com.plonit.plonitservice.api.feed.controller.request.SaveFeedReq;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.feed.Feed;
import com.plonit.plonitservice.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class SaveFeedDto {

    private long memberKey;

    private long crewId;

    private String content;

    private List<MultipartFile> feedPictures;

    public static SaveFeedDto of (Long memberKey, SaveFeedReq saveFeedReq) {
        return SaveFeedDto.builder()
                .memberKey(memberKey)
                .crewId(Long.parseLong(saveFeedReq.getCrewId()))
                .content(saveFeedReq.getContent())
                .feedPictures(saveFeedReq.getFeedPictures())
                .build();
    }

    public static Feed toEntity (Member member, Crew crew, SaveFeedDto saveCrewDto) {
        return Feed.builder()
                .member(member)
                .crew(crew)
                .content(saveCrewDto.getContent())
                .build();
    }

}