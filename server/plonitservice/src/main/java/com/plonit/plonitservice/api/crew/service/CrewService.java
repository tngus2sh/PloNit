package com.plonit.plonitservice.api.crew.service;

import com.plonit.plonitservice.api.crew.service.dto.ApproveCrewDto;
import com.plonit.plonitservice.api.crew.service.dto.SaveCrewDto;
import com.plonit.plonitservice.api.crew.service.dto.SaveCrewNoticeDto;
import com.plonit.plonitservice.api.crew.service.dto.UpdateCrewNoticeDto;

public interface CrewService {
    void saveCrew(SaveCrewDto saveCrewDTO);
    void joinCrewMember(Long memberId, Long crewId);
    void approveCrewMember(ApproveCrewDto approveCrewDto);
    void saveCrewNotice(SaveCrewNoticeDto saveCrewNoticeDto);

}
