package com.plonit.plonitservice.api.member.service;

import com.plonit.plonitservice.api.member.controller.request.UpdateMemberReq;
import com.plonit.plonitservice.api.member.controller.response.FindMemberRes;
import com.plonit.plonitservice.api.member.service.dto.UpdateMemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

public interface MemberService {
    FindMemberRes updateMember(UpdateMemberDto updateMemberDto);
    // findMember();
    // findCrew();
    // findFloggings();
    // findFlogging();
    // findMemberRanking();
    // findCrewRanking();
    // findMissionBadge();
    // findRankingBadge();

}
