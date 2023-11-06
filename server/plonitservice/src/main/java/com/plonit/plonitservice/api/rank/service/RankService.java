package com.plonit.plonitservice.api.rank.service;

import com.plonit.plonitservice.api.rank.controller.response.CrewAvgResponse;
import com.plonit.plonitservice.api.rank.controller.response.CrewTotalResponse;
import com.plonit.plonitservice.api.rank.controller.response.MembersRankResponse;

import java.util.List;

public interface RankService {

    public MembersRankResponse findAllMembersRank(Long memberKey);

    public CrewTotalResponse findAllCrewRank(Long memberKey);

    public CrewAvgResponse findAllCrewRankByAVG(Long crewId);

}
