package com.plonit.plonitservice.api.member.service;

import com.plonit.plonitservice.api.member.controller.request.UpdateMemberReq;
import com.plonit.plonitservice.api.member.controller.response.FindMemberRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

public interface MemberService {
    FindMemberRes updateMember(Long userId, UpdateMemberReq updateMemberReq);
    // findMember();
    // findCrew();
    // findFloggings();
    // findFlogging();
    // findMemberRanking();
    // findCrewRanking();
    // findMissionBadge();
    // findRankingBadge();

}
