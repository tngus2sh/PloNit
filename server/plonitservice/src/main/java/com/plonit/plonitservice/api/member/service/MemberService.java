package com.plonit.plonitservice.api.member.service;

import com.plonit.plonitservice.api.member.controller.response.FindCrewInfoRes;
import com.plonit.plonitservice.api.member.controller.response.FindMemberInfoRes;
import com.plonit.plonitservice.api.member.controller.response.FindMemberRes;
import com.plonit.plonitservice.api.member.service.dto.UpdateMemberDto;
import com.plonit.plonitservice.api.member.service.dto.UpdateVolunteerInfoDto;

import java.util.List;

public interface MemberService {
    FindMemberRes updateMember(UpdateMemberDto updateMemberDto);
    FindMemberInfoRes findMemberInfo();
    List<FindCrewInfoRes> findCrewInfo();
    void updateVolunteerInfo(UpdateVolunteerInfoDto dto);
    // findFloggings();
    // findFlogging();
    // findMemberRanking();
    // findCrewRanking();
    // findMissionBadge();
    // findRankingBadge();

}
