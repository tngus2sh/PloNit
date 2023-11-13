package com.plonit.plonitservice.api.crewping.service;

import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingMembersByMasterRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingMembersRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingsRes;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CrewpingQueryService {
    List<FindCrewpingsRes> findCrewpings(Long memberId, Long crewId);
    FindCrewpingRes findCrewping(Long memberId, Long crewpingId);
    List<FindCrewpingMembersByMasterRes> findCrewpingMembersByMaster(Long memberId, Long crewpingId);
    List<FindCrewpingMembersRes> findCrewpingMembers(Long memberId, Long crewpingId);
}
