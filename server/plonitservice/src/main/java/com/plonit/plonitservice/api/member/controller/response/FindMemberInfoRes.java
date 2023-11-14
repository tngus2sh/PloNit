package com.plonit.plonitservice.api.member.controller.response;

import com.plonit.plonitservice.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class FindMemberInfoRes {

    private Long memberId;
    private String email;
    private String name;
    private String nickname;
    private String profileImage;
    private Boolean gender;
    private String birth;
    private String region;
    private Long dongCode;
    private Float height;
    private Float weight;
    private String id1365;
    private Integer ploggingCount;
    private Integer crewCount;
    private Integer badgeCount;


    public static FindMemberInfoRes of(Member member, Integer ploggingCount, Integer crewCount, Integer badgeCount) {
        return FindMemberInfoRes.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .gender(member.getGender())
                .birth(member.getBirth())
                .region(member.getRegion())
                .dongCode(member.getDongCode())
                .height(member.getHeight())
                .weight(member.getWeight())
                .id1365(member.getId1365())
                .ploggingCount(ploggingCount)
                .crewCount(crewCount)
                .badgeCount(badgeCount)
                .build();
    }

}