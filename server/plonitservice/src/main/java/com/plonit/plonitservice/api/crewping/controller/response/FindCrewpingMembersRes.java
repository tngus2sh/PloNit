package com.plonit.plonitservice.api.crewping.controller.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class FindCrewpingMembersRes {

    private String profileImage;
    private String nickname;


    @Builder
    public FindCrewpingMembersRes(String profileImage, String nickname) {
        this.profileImage = profileImage;
        this.nickname = nickname;
    }
}
