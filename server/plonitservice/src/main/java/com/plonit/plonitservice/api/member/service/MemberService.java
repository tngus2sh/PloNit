package com.plonit.plonitservice.api.member.service;

import com.plonit.plonitservice.api.member.controller.request.UpdateMemberReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

public interface MemberService {
    boolean updateMember(Long userId, UpdateMemberReq updateMemberReq);
    // findMember();
    // findCrew();
    // findFloggings();
    // findFlogging();
    // findMemberRanking();
    // findCrewRanking();
    // findMissionBadge();
    // findRankingBadge();

}
