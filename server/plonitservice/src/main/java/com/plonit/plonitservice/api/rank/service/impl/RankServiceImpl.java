package com.plonit.plonitservice.api.rank.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.plonit.plonitservice.api.crew.controller.response.CrewRankRes;
import com.plonit.plonitservice.api.member.controller.response.MemberRankRes;
import com.plonit.plonitservice.api.member.service.MemberService;
import com.plonit.plonitservice.api.rank.controller.response.CrewAvgResponse;
import com.plonit.plonitservice.api.rank.controller.response.CrewTotalResponse;
import com.plonit.plonitservice.api.rank.controller.response.MembersRankResponse;
import com.plonit.plonitservice.api.rank.service.RankService;
import com.plonit.plonitservice.common.enums.Rank;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.exception.ErrorCode;
import com.plonit.plonitservice.common.util.RedisUtils;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import com.plonit.plonitservice.domain.rank.RankingPeriod;
import com.plonit.plonitservice.domain.rank.repository.RankingPeriodQueryRepository;
import com.plonit.plonitservice.domain.rank.repository.RankingPeriodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;
import static com.plonit.plonitservice.common.exception.ErrorCode.RANKING_PERIOD_NOT_FOUND;


@Service
@Slf4j
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {
    
    private final MemberQueryRepository memberQueryRepository;
    private final CrewQueryRepository crewQueryRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final RankingPeriodQueryRepository rankingPeriodQueryRepository;
    private final RankingPeriodRepository rankingPeriodRepository;
    private final RedisUtils redisUtils;

    /**
     * 전체 회원 랭킹 조회
     * @param memberKey 멤버 식별키
     * @return 전체 회원 랭킹
     */
    @Override
    public MembersRankResponse findAllMembersRank(Long memberKey) {

        MembersRankResponse membersRankResponse = new MembersRankResponse();

        /* 랭킹 기간 생성 */
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        // 이번 달의 첫째 날을 얻습니다.
        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);

        // 첫째 날의 첫 시간을 LocalDateTime으로 가져옵니다.
        LocalDateTime firstDateTimeOfThisMonth = firstDayOfThisMonth.atStartOfDay();


        RankingPeriod rankingPeriodTest = null;
        rankingPeriodTest = RankingPeriod.builder()
                .startDate(firstDateTimeOfThisMonth)
                .endDate(firstDateTimeOfThisMonth.plusDays(13))
                .build();
        rankingPeriodRepository.save(rankingPeriodTest);

        
        // 현재 랭킹 기간 조회
        RankingPeriod rankingPeriod = nowRankingPeriod();
        membersRankResponse.setStartDate(rankingPeriod.getStartDate());
        membersRankResponse.setEndDate(rankingPeriod.getEndDate());

        // Redis에서 랭킹 조회
        Set<ZSetOperations.TypedTuple<String>> sortedSetRangeWithScores = redisUtils.getSortedSetRangeWithScores(Rank.MEMBER.getDescription(), 0, 9);

        List<MembersRankResponse.MembersRank> membersRanks = membersRankResponse.getMembersRanks();
        
        List<Long> memberIds = new LinkedList<>();
        Map<Long, Object[]> distanceRankings = new HashMap<>();
        
        // 멤버 식별키와 누적거리 및 랭킹 저장
        Integer index = 0;
        for (ZSetOperations.TypedTuple<String> sortedSetRangeWithScore : sortedSetRangeWithScores) {
            Long memberId = Long.parseLong(sortedSetRangeWithScore.getValue());
            Double distance = sortedSetRangeWithScore.getScore();

            memberIds.add(memberId);
            distanceRankings.put(memberId, new Object[]{distance, ++index});
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
    public CrewTotalResponse findAllCrewRank(Long memberKey) {
        
        // memberKey로 crewId 가져오기
        Optional<Long> crewMemberByMemberId = crewMemberRepository.findCrewMemberByMemberId(memberKey);
        Long crewId = -1L;
        if (crewMemberByMemberId.isPresent()) {
            crewId = crewMemberByMemberId.get();
        }

        CrewTotalResponse crewTotalResponse = new CrewTotalResponse();

        MembersRankResponse membersRankResponse = new MembersRankResponse();

        // 현재 랭킹 기간 조회
        RankingPeriod rankingPeriod = nowRankingPeriod();
        membersRankResponse.setStartDate(rankingPeriod.getStartDate());
        membersRankResponse.setEndDate(rankingPeriod.getEndDate());

        
        // Redis에서 랭킹 조회
        Set<ZSetOperations.TypedTuple<String>> sortedSetRangeWithScores = redisUtils.getSortedSetRangeWithScores(Rank.CREW.getDescription(), 0, 9);

        List<CrewTotalResponse.CrewsRanks> crewsRanks = crewTotalResponse.getCrewsRanks();
        
        List<Long> crewIds = new LinkedList<>();
        Map<Long, Object[]> disanceRankings = new HashMap<>();
        
        // 크루 식별키, 누적 거리, 랭킹 순위 정리
        Integer index = 0;
        for (ZSetOperations.TypedTuple<String> sortedSetRangeWithScore : sortedSetRangeWithScores) {
            Long crewKey = Long.valueOf(sortedSetRangeWithScore.getValue());
            Double distance = sortedSetRangeWithScore.getScore();

            crewIds.add(crewKey);
            disanceRankings.put(crewKey, new Object[]{distance, ++index});
        }

        // 크루 이미지, 크루 닉네임 가져오기
        List<CrewRankRes> crewRankResList = crewQueryRepository.findByIds(crewIds);

        for (CrewRankRes crewRankRes : crewRankResList) {
            Long crewKey = crewRankRes.getId();

            // 내 크루인지 확인
            boolean isMine = false;
            if (crewKey.equals(crewId)) {
                isMine = true;
            }
            
            // 누적 거리와 랭킹
            Object[] distanceRanking = disanceRankings.get(crewKey);
            Double distance = (Double) distanceRanking[0];
            Integer ranking = (Integer) distanceRanking[1];

            crewsRanks.add(CrewTotalResponse.CrewsRanks.builder()
                    .nickName(crewRankRes.getName())
                    .profileImage(crewRankRes.getCrewImage())
                    .ranking(ranking)
                    .distance(distance)
                    .isMine(isMine)
                    .build());
        }
        
        crewTotalResponse.setCrewsRanks(crewsRanks);
        
        return crewTotalResponse;
    }

    @Override
    public CrewAvgResponse findAllCrewRankByAVG(Long memberKey) {

        // memberKey로 crewId 가져오기
        Optional<Long> crewMemberByMemberId = crewMemberRepository.findCrewMemberByMemberId(memberKey);
        Long crewId = -1L;
        if (crewMemberByMemberId.isPresent()) {
            crewId = crewMemberByMemberId.get();
        }
        
        CrewAvgResponse crewAvgResponse = new CrewAvgResponse();

        MembersRankResponse membersRankResponse = new MembersRankResponse();

        // 현재 랭킹 기간 조회
        RankingPeriod rankingPeriod = nowRankingPeriod();
        membersRankResponse.setStartDate(rankingPeriod.getStartDate());
        membersRankResponse.setEndDate(rankingPeriod.getEndDate());


        // Redis에서 랭킹 조회
        Set<ZSetOperations.TypedTuple<String>> sortedSetRangeWithScores = redisUtils.getSortedSetRangeWithScores(Rank.CREW_AVG.getDescription(), 0, 9);

        List<CrewAvgResponse.CrewsAvgRanks> crewsAvgRanks = crewAvgResponse.getCrewsAvgRanks();

        List<Long> crewIds = new LinkedList<>();
        Map<Long, Object[]> disanceRankings = new HashMap<>();

        // 크루 식별키, 평균 거리, 랭킹 정리
        Integer index = 0;
        for (ZSetOperations.TypedTuple<String> sortedSetRangeWithScore : sortedSetRangeWithScores) {
            Long crewKey = Long.valueOf(sortedSetRangeWithScore.getValue());
            Double distance = sortedSetRangeWithScore.getScore();

            crewIds.add(crewKey);
            disanceRankings.put(crewKey, new Object[]{distance, ++index});
        }

        // 크루 이미지, 크루 닉네임 가져오기
        List<CrewRankRes> crewRankResList = crewQueryRepository.findByIds(crewIds);

        for (CrewRankRes crewRankRes : crewRankResList) {
            Long crewKey = crewRankRes.getId();

            // 내 크루인지 확인
            boolean isMine = false;
            if (crewKey.equals(crewId)) {
                isMine = true;
            }

            // 누적 거리와 랭킹
            Object[] distanceRanking = disanceRankings.get(crewKey);
            Double distance = (Double) distanceRanking[0];
            Integer ranking = (Integer) distanceRanking[1];

            crewsAvgRanks.add(CrewAvgResponse.CrewsAvgRanks.builder()
                    .nickName(crewRankRes.getName())
                    .profileImage(crewRankRes.getCrewImage())
                    .ranking(ranking)
                    .distance(distance)
                    .isMine(isMine)
                    .build());
        }
        
        crewAvgResponse.setCrewsAvgRanks(crewsAvgRanks);

        return crewAvgResponse;
    }

    /**
     * 현재 랭킹 기간 조회
     * @return 현재 랭킹 기간
     */
    private RankingPeriod nowRankingPeriod() {
        Optional<RankingPeriod> rankingPeriodOptional = rankingPeriodQueryRepository.findRecentId();
        if (rankingPeriodOptional.isPresent()) {
            return rankingPeriodOptional.get();
        }
        return null;
    }
}
