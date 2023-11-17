package com.plonit.plonitservice.api.member.controller.response;

import com.plonit.plonitservice.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;

@Getter
@Builder
@ToString
public class FindMemberRes {
    private Long memberId;

    private String name;

    private String nickname;

    private String profileImage;

    private Boolean gender;

    private String region;

    private Long dongCode;

    private Float height;

    private Float weight;

    private String id1365;

    private String birth;

    public static FindMemberRes of(Member member) {
        return FindMemberRes.builder()
                .memberId(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .gender(member.getGender())
                .region(member.getRegion())
                .dongCode(member.getDongCode())
                .height(member.getHeight())
                .weight(member.getWeight())
                .id1365(member.getId1365())
                .birth(member.getBirth())
                .build();
    }

}