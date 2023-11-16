package com.plonit.plonitservice.api.crew.service.impl;

import com.plonit.plonitservice.api.crew.controller.response.*;
import com.plonit.plonitservice.api.crew.service.CrewQueryService;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.enums.Rank;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.util.RedisUtils;
import com.plonit.plonitservice.common.util.RequestUtils;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.CrewMember;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberQueryRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewQueryRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewRepository;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.plonit.plonitservice.common.exception.ErrorCode.*;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.END;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CrewQueryServiceImpl implements CrewQueryService {

    private final CrewRepository crewRepository;
    private final CrewQueryRepository crewQueryRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final CrewMemberQueryRepository crewMemberQueryRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    private final AwsS3Uploader awsS3Uploader;
    private final RedisUtils redisUtils;

    @Override // 크루 목록 조회
    public List<FindCrewsRes> findCrews() {
        log.info(logCurrent(getClassName(), getMethodName(), START));
//        List<Crew> crew = crewRepository.findAll();
        List<FindCrewsRes> crewList = crewQueryRepository.findCrews();

        if(crewList.isEmpty())
            return Collections.emptyList();

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return crewList;
    }

    @Override // 크루 상세 조회
    public FindCrewRes findCrew(Long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberId = RequestUtils.getMemberId();

        FindCrewRes findCrewRes = crewQueryRepository.findCrewWithCrewMember(crewId, memberId)
                .orElseThrow(() -> new CustomException(CREW_NOT_FOUND));

        // 랭킹 정보 가져오기
        int index = 0;
        Set<ZSetOperations.TypedTuple<String>> rankCrewSorts = redisUtils.getSortedSetRangeWithScores(Rank.CREW.getDescription(), 0, -1);
        for (ZSetOperations.TypedTuple<String> rankCrewSort : rankCrewSorts) {
            ++index;
            String value = rankCrewSort.getValue();
            if (value.equals(String.valueOf(crewId))) {
                findCrewRes.setTotalRanking(index);
                findCrewRes.setTotalDistance(rankCrewSort.getScore());
            }
        }

        index = 0;
        Set<ZSetOperations.TypedTuple<String>> rankCrewAvgSorts = redisUtils.getSortedSetRangeWithScores(Rank.CREW_AVG.getDescription(), 0, -1);
        for (ZSetOperations.TypedTuple<String> rankCrewAvgSort : rankCrewAvgSorts) {
            ++index;
            String value = rankCrewAvgSort.getValue();
            if (value.equals(String.valueOf(crewId))) {
                findCrewRes.setAvgRanking(index);
                findCrewRes.setAvgDistance(rankCrewAvgSort.getScore());
            }
        }



        log.info(logCurrent(getClassName(), getMethodName(), END));
        return findCrewRes;
    }

    @Override // 크루원 조회
    public List<FindCrewMemberRes> findCrewMember(Long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        List<CrewMember> crewMemberList = crewMemberQueryRepository.findByCrewId(crewId);

        if (crewMemberList.isEmpty()) {
            throw new CustomException(CREW_NOT_FOUND);
        }

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return crewMemberList.stream().map(FindCrewMemberRes::of).collect(Collectors.toList());
    }

    @Override // 크루원 조회(크루장)
    public List<FindCrewMasterMemberRes> findCrewMasterMember(Long memberId, Long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        if(!crewMemberQueryRepository.isValidCrewMember(memberId, crewId, true))
            throw new CustomException(CREW_MASTER_NOT_FORBIDDEN);

        List<CrewMember> crewMemberList = crewMemberQueryRepository.findByCrewId(crewId);
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return crewMemberList.stream().map(FindCrewMasterMemberRes::of).collect(Collectors.toList());
    }

    @Override // 크루 가입 대기 조회
    public List<FindWaitingCrewMemberRes> findWaitingCrewMember(Long memberId, Long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        if(!crewMemberQueryRepository.isValidCrewMember(memberId, crewId, true))
            throw new CustomException(CREW_MASTER_NOT_FORBIDDEN);

        List<CrewMember> crewMemberList = crewMemberQueryRepository.findByWaitingCrewId(crewId);
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return crewMemberList.stream().map(FindWaitingCrewMemberRes::of).collect(Collectors.toList());
    }

    @Override // 크루 검색
    public List<SearchCrewsRes> searchCrew(int type, String word) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        List<SearchCrewsRes> searchCrewsResList = crewQueryRepository.SearchCrew(type, word);

        if(searchCrewsResList.isEmpty())
            return Collections.emptyList();

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return searchCrewsResList;
    }

}
