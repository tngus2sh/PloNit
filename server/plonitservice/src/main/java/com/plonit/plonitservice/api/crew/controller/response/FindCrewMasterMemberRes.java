package com.plonit.plonitservice.api.crew.controller.response;

import com.plonit.plonitservice.domain.crew.CrewMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FindCrewMasterMemberRes {
    private long crewMemberId;
    private String profileImage;
    private String nickname;
    public static FindCrewMasterMemberRes of(CrewMember crewMember) {
        return FindCrewMasterMemberRes.builder()
                .crewMemberId(crewMember.getMember().getId())
                .profileImage(crewMember.getMember().getProfileImage())
                .nickname(crewMember.getMember().getNickname())
                .build();
    }
}
