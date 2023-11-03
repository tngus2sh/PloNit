package com.plonit.plonitservice.api.feed.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeedCommentDto {
    String nickname;
    String profileImage;
    String content;
}