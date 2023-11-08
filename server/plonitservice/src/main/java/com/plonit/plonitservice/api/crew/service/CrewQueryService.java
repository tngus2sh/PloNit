package com.plonit.plonitservice.api.crew.service;

import com.plonit.plonitservice.api.crew.controller.response.*;

import java.util.List;

public interface CrewQueryService {
    List<FindCrewsRes> findCrews();
    FindCrewRes findCrew(Long crewId);
    List<FindCrewMemberRes> findCrewMember(Long crewId);
    List<FindCrewMasterMemberRes> findCrewMasterMember(Long memberId, Long crewId);
    List<FindWaitingCrewMemberRes> findWaitingCrewMember(Long memberId, Long crewId);
}