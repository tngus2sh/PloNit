package com.plonit.plonitservice.api.crew.service;

import com.plonit.plonitservice.api.crew.service.dto.ApproveCrewDto;
import com.plonit.plonitservice.api.crew.service.dto.SaveCrewDto;

public interface CrewService {
    void saveCrew(SaveCrewDto saveCrewDTO);
    void joinCrewMember(Long memberId, Long crewId);
    void approveCrewMember(ApproveCrewDto approveCrewDto);

}
