package com.plonit.plonitservice.api.member.service.impl;

import com.plonit.plonitservice.api.member.controller.response.VolunteerMemberInfoRes;
import com.plonit.plonitservice.api.member.service.MemberQueryService;
import com.plonit.plonitservice.common.util.RequestUtils;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    @Override
    public List<VolunteerMemberInfoRes> findVolunteerInfo(List<Long> memberIdList) {

        List<VolunteerMemberInfoRes> result = memberIdList.stream().map(memberQueryRepository::findVolunteerMemberInfo).collect(Collectors.toList());

        return result;
    }
}
