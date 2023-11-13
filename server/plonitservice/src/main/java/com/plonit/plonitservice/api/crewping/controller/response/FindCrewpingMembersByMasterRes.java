package com.plonit.plonitservice.api.crewping.controller.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class FindCrewpingMembersByMasterRes {

    private Long id;
    private String profileImage;
    private String nickname;


    @Builder
    public FindCrewpingMembersByMasterRes(Long id, String profileImage, String nickname) {
        this.id = id;
        this.profileImage = profileImage;
        this.nickname = nickname;
    }
}
