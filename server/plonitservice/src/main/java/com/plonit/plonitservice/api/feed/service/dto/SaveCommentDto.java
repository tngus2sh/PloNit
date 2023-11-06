package com.plonit.plonitservice.api.feed.service.dto;

import com.plonit.plonitservice.api.feed.controller.request.SaveCommentReq;
import com.plonit.plonitservice.domain.feed.Comment;
import com.plonit.plonitservice.domain.feed.Feed;
import com.plonit.plonitservice.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveCommentDto {

    private long memberKey;

    private long feedId;

    private String content;
    public static SaveCommentDto of (Long memberKey,SaveCommentReq saveCommentReq) {
        return SaveCommentDto.builder()
                .memberKey(memberKey)
                .feedId(saveCommentReq.getFeedId())
                .content(saveCommentReq.getContent())
                .build();
    }

    public static Comment toEntity (Member member, Feed feed, SaveCommentDto saveCommentDto) {
        return Comment.builder()
                .member(member)
                .feed(feed)
                .content(saveCommentDto.getContent())
                .build();
    }
}