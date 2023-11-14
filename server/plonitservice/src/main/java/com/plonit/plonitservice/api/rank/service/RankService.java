package com.plonit.plonitservice.api.rank.service;

import com.plonit.plonitservice.api.rank.controller.response.CrewAvgRes;
import com.plonit.plonitservice.api.rank.controller.response.CrewTotalRes;
import com.plonit.plonitservice.api.rank.controller.response.MembersRankRes;

public interface RankService {

    public MembersRankRes findAllMembersRank(Long memberKey);

    public CrewTotalRes findAllCrewRank(Long memberKey);
    
    public CrewAvgRes findAllCrewRankByAVG(Long memberKey);
    
}
