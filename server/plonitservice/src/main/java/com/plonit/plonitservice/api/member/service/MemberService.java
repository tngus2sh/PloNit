package com.plonit.plonitservice.api.member.service;

import com.plonit.plonitservice.api.member.controller.response.FindMemberInfoRes;
import com.plonit.plonitservice.api.member.controller.response.FindMemberRes;
import com.plonit.plonitservice.api.member.service.dto.UpdateMemberDto;

public interface MemberService {
    FindMemberRes updateMember(UpdateMemberDto updateMemberDto);
    FindMemberInfoRes findMemberInfo();
    // findMember();
    // findCrew();
    // findFloggings();
    // findFlogging();
    // findMemberRanking();
    // findCrewRanking();
    // findMissionBadge();
    // findRankingBadge();

}
