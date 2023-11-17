package com.plonit.plonitservice.api.feed.controller.response;

import com.plonit.plonitservice.api.feed.service.dto.FeedCommentDto;
import com.plonit.plonitservice.api.feed.service.dto.FeedPictureDto;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FindFeedRes {
    private long id;
    private String content;
    private String nickname;
    private String profileImage;
    private Boolean isMine;
    private String createdDate;
    private List<FeedPictureDto> feedPictures;

    @Setter
    private int likeCount;
    @Setter
    private Boolean isLike;

    private List<FeedCommentDto> comments;
}