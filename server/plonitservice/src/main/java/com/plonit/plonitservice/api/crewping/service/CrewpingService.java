package com.plonit.plonitservice.api.crewping.service;

import com.plonit.plonitservice.api.crewping.service.dto.SaveCrewpingDto;
import com.plonit.plonitservice.api.crewping.service.dto.SaveCrewpingRecordDto;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface CrewpingService {

    void saveCrewping(SaveCrewpingDto dto);
    void joinCrewping(Long memberId, Long crewpingId);
    void quitCrewping(Long memberId, Long crewpingId);
    void kickoutCrewpingMember(Long memberId, Long crewpingId, Long targetId);
    void saveCrewpingRecord(SaveCrewpingRecordDto dto);
}
