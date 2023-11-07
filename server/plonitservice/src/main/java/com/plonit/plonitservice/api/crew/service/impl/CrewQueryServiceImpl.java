package com.plonit.plonitservice.api.crew.service.impl;

import com.plonit.plonitservice.api.crew.controller.response.FindCrewMasterMemberRes;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewMemberRes;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewRes;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewsRes;
import com.plonit.plonitservice.api.crew.service.CrewQueryService;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.exception.CustomException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
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
    public FindCrewRes findCrew(long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        FindCrewRes findCrewRes = crewQueryRepository.findCrewWithCrewMember(crewId)
                .orElseThrow(() -> new CustomException(CREW_NOT_FOUND));

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return findCrewRes;
    }

    @Override // 크루원 조회
    public List<FindCrewMemberRes> findCrewMember(long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        List<CrewMember> crewMemberList = crewMemberQueryRepository.findByCrewId(crewId);

        if (crewMemberList.isEmpty()) {
            throw new CustomException(CREW_NOT_FOUND);
        }

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return crewMemberList.stream().map(FindCrewMemberRes::of).collect(Collectors.toList());
    }

    @Override // 크루원 조회(크루장)
    public List<FindCrewMasterMemberRes> findCrewMasterMember(long memberId, long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        if(!crewMemberQueryRepository.isValidCrewMaster(memberId, crewId))
            throw new CustomException(CREW_MASTER_NOT_FORBIDDEN);

        List<CrewMember> crewMemberList = crewMemberQueryRepository.findByCrewId(crewId);
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return crewMemberList.stream().map(FindCrewMasterMemberRes::of).collect(Collectors.toList());
    }

}
