package com.plonit.plonitservice.api.crew.service.impl;

import com.plonit.plonitservice.api.crew.controller.response.FindCrewRes;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewsRes;
import com.plonit.plonitservice.api.crew.service.CrewQueryService;
import com.plonit.plonitservice.common.AwsS3Uploader;
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
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    private final AwsS3Uploader awsS3Uploader;

    public List<FindCrewsRes> findCrews() { // 크루 목록 조회
        log.info(logCurrent(getClassName(), getMethodName(), START));
        List<FindCrewsRes> crewList = crewQueryRepository.findCrews();

        if(crewList.isEmpty())
            return Collections.emptyList();

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return crewList;
    }

    public FindCrewRes findCrew(long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        FindCrewRes findCrewRes = null;
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return findCrewRes;
    }

}
