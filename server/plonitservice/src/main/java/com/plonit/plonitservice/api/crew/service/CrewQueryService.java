package com.plonit.plonitservice.api.crew.service;

import com.plonit.plonitservice.api.crew.controller.response.FindCrewRes;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewsRes;

import java.util.List;

public interface CrewQueryService {
    List<FindCrewsRes> findCrews();

    FindCrewRes findCrew(long crewId);
}