package com.plonit.plonitservice.api.rank.service.impl;

import com.plonit.plonitservice.api.rank.controller.response.CrewAvgResponse;
import com.plonit.plonitservice.api.rank.controller.response.CrewTotalResponse;
import com.plonit.plonitservice.api.rank.controller.response.MembersRankResponse;
import com.plonit.plonitservice.api.rank.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RankServiceImpl implements RankService {

    @Override
    public MembersRankResponse findAllMembersRank(Long memberKey) {

        // Redis에서 랭킹 조회

        return null;
    }

    @Override
    public CrewTotalResponse findAllCrewRank(Long crewId) {
        return null;
    }

    @Override
    public CrewAvgResponse findAllCrewRankByAVG(Long crewId) {
        return null;
    }
}
