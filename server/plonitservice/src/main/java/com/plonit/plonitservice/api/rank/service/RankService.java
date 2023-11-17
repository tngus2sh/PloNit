package com.plonit.plonitservice.api.rank.service;

import com.plonit.plonitservice.api.rank.controller.response.*;

import java.util.List;

public interface RankService {

    public MembersRankRes findAllMembersRank(Long memberKey);

    public CrewTotalRes findAllCrewRank(Long memberKey);
    
    public CrewAvgRes findAllCrewRankByAVG(Long memberKey);

    public List<FindMyRankingRes> findMyRanking(Long memberKey);

    public List<FindMyCrewRankingRes> findMyCrewRanking(Long memberKey);
    
}
