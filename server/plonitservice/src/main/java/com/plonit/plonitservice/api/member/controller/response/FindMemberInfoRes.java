package com.plonit.plonitservice.api.member.controller.response;

import com.plonit.plonitservice.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class FindMemberInfoRes {

    private String email;
    private String name;
    private String nickname;
    private String profileImage;
    private Integer ploggingCount;
    private Integer crewCount;
    private Integer badgeCount;


    public static FindMemberInfoRes of(Member member, Integer ploggingCount, Integer crewCount, Integer badgeCount) {
        return FindMemberInfoRes.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .ploggingCount(ploggingCount)
                .crewCount(crewCount)
                .badgeCount(badgeCount)
                .build();
    }

}