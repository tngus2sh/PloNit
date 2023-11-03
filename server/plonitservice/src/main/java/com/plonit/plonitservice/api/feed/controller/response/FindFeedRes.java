package com.plonit.plonitservice.api.feed.controller.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FindFeedRes {
    private long id;
    private String nickname;
    private String profileImage;
    private String content;
    private List<String> feedPicture;
    private int isLike;
    private List<Comment> comments;
    private int totalRanking;
    private int avgRanking;
    public class Comment {
        String nickname;
        String profileImage;
        String content;
    }
}