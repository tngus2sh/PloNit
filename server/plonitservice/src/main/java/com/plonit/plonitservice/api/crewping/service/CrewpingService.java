package com.plonit.plonitservice.api.crewping.service;

import com.plonit.plonitservice.api.crewping.service.dto.SaveCrewpingDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CrewpingService {

    void saveCrewping(SaveCrewpingDto dto);
}
