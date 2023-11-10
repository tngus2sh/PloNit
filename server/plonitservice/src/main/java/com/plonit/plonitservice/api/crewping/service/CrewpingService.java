package com.plonit.plonitservice.api.crewping.service;

import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingMembersRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingsRes;
import com.plonit.plonitservice.api.crewping.service.dto.SaveCrewpingDto;
import com.plonit.plonitservice.api.crewping.service.dto.SaveCrewpingRecordDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CrewpingService {

    void saveCrewping(SaveCrewpingDto dto);
    List<FindCrewpingsRes> findCrewpings(Long memberId, Long crewId);
    FindCrewpingRes findCrewping(Long memberId, Long crewpingId);
    void joinCrewping(Long memberId, Long crewpingId);
    void quitCrewping(Long memberId, Long crewpingId);
    List<FindCrewpingMembersRes> findCrewpingMembers(Long memberId, Long crewpingId);
    void kickoutCrewpingMember(Long memberId, Long crewpingId, Long targetId);
    void saveCrewpingRecord(SaveCrewpingRecordDto dto);
}
