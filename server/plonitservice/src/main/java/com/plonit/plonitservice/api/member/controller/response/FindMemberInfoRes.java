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
    private Boolean gender;
    private String birth;
    private String region;
    private Float height;
    private Float weight;
    private String id1365;


    public static FindMemberInfoRes of(Member member) {
        return FindMemberInfoRes.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .gender(member.getGender())
                .birth(member.getBirth())
                .region(member.getRegion())
                .height(member.getHeight())
                .weight(member.getWeight())
                .id1365(member.getId1365())
                .build();
    }

}