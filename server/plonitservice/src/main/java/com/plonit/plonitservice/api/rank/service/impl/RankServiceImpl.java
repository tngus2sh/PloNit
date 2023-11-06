package com.plonit.plonitservice.api.rank.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.plonit.plonitservice.api.member.controller.response.MemberRankRes;
import com.plonit.plonitservice.api.member.service.MemberService;
import com.plonit.plonitservice.api.rank.controller.response.CrewAvgResponse;
import com.plonit.plonitservice.api.rank.controller.response.CrewTotalResponse;
import com.plonit.plonitservice.api.rank.controller.response.MembersRankResponse;
import com.plonit.plonitservice.api.rank.service.RankService;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.exception.ErrorCode;
import com.plonit.plonitservice.common.util.RedisUtils;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;
import static com.plonit.plonitservice.common.exception.ErrorCode.RANKING_PERIOD_NOT_FOUND;


@Service
@Slf4j
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {
    
    private final MemberQueryRepository memberQueryRepository;
    private final RedisUtils redisUtils;

    /**
     * 전체 회원 랭킹 조회
     * @param memberKey 멤버 식별키
     * @return 전체 회원 랭킹
     */
    @Override
    public MembersRankResponse findAllMembersRank(Long memberKey) {

        // 현재 랭킹 기간 조회
        String rankingPeriod = null;
        try {
            rankingPeriod = redisUtils.getRedisValue("RANKING-PERIOD", String.class);
        } catch (JsonProcessingException e) {
            throw new CustomException(RANKING_PERIOD_NOT_FOUND);
        }

        // Redis에서 랭킹 조회
        Set<ZSetOperations.TypedTuple<String>> sortedSetRangeWithScores = redisUtils.getSortedSetRangeWithScores("MEMBER-RANK", 0, 9);

        MembersRankResponse membersRankResponse = new MembersRankResponse();
        membersRankResponse.setRankingPeriod(rankingPeriod);
        List<MembersRankResponse.MembersRank> membersRanks = membersRankResponse.getMembersRanks();
        
        List<Long> memberIds = new LinkedList<>();
        Map<Long, Object[]> distanceRankings = new HashMap<>();
        
        // 멤버 식별키와 누적거리 및 랭킹 저장
        int index = 0;
        for (ZSetOperations.TypedTuple<String> sortedSetRangeWithScore : sortedSetRangeWithScores) {
            Long memberId = Long.parseLong(sortedSetRangeWithScore.getValue());
            Double distance = sortedSetRangeWithScore.getScore();

            memberIds.add(memberId);
            distanceRankings.put(memberId, new Object[]{distance, index + 1});
        }

        // 닉네임, 프로필이미지, 랭킹, 거리, 내꺼인지 확인
        List<MemberRankRes> memberRankResList = memberQueryRepository.findByIds(memberIds);

        for (MemberRankRes memberRankRes : memberRankResList) {
            Long memberId = memberRankRes.getMemberId();
            
            // 내꺼인지 확인
            boolean isMine = false;
            if (memberId.equals(memberKey)) {
                isMine = true;
            }
            
            // 누적 거리와 랭킹
            Object[] distanceRanking = distanceRankings.get(memberRankRes.getMemberId());
            Double distance = (Double) distanceRanking[0];
            Integer ranking = (Integer) distanceRanking[1];

            membersRanks.add(MembersRankResponse.MembersRank.builder()
                    .nickName(memberRankRes.getNickName())
                    .profileImage(memberRankRes.getProfileImage())
                    .ranking(ranking)
                    .distance(distance)
                    .isMine(isMine)
                    .build());
        }
        
        membersRankResponse.setMembersRanks(membersRanks);
        
        return membersRankResponse;
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
