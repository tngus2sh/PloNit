package com.plonit.plonitservice.api.member.service.impl;

import com.plonit.plonitservice.api.member.controller.response.FindCrewpingInfoRes;
import com.plonit.plonitservice.api.member.service.MemberQueryService;
import com.plonit.plonitservice.common.util.RequestUtils;
import com.plonit.plonitservice.domain.crewping.repository.CrewpingQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {

    private final CrewpingQueryRepository crewpingQueryRepository;

    @Override
    public List<FindCrewpingInfoRes> findCrewpingInfo() {
        Long memberId = RequestUtils.getMemberId();

        List<FindCrewpingInfoRes> result = crewpingQueryRepository.findCrewpingInfo(memberId);

        return result;
    }
}
