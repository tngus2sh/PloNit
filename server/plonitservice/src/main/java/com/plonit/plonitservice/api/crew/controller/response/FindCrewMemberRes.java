package com.plonit.plonitservice.api.crew.controller.response;

import com.plonit.plonitservice.domain.crew.CrewMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FindCrewMemberRes {
    private String profileImage;
    private String nickname;
    public static FindCrewMemberRes of(CrewMember crewMember) {
        return FindCrewMemberRes.builder()
                .profileImage(crewMember.getMember().getProfileImage())
                .nickname(crewMember.getMember().getNickname())
                .build();
    }
}
