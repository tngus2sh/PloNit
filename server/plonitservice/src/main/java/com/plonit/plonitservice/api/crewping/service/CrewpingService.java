package com.plonit.plonitservice.api.crewping.service;

import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingsRes;
import com.plonit.plonitservice.api.crewping.service.dto.SaveCrewpingDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CrewpingService {

    void saveCrewping(SaveCrewpingDto dto);
    List<FindCrewpingsRes> findCrewpings(Long memberId, Long crewId);
    FindCrewpingRes findCrewping(Long memberId, Long crewpingId);
}
