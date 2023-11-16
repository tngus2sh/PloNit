package com.plonit.plonitservice.api.member.service.impl;

import com.plonit.plonitservice.api.crew.controller.response.FindCrewsRes;
import com.plonit.plonitservice.api.feed.service.dto.FeedPictureDto;
import com.plonit.plonitservice.api.member.controller.PloggingFeignClient;
import com.plonit.plonitservice.api.member.controller.response.FindCrewInfoRes;
import com.plonit.plonitservice.api.member.controller.response.FindMemberInfoRes;
import com.plonit.plonitservice.api.member.controller.response.FindMemberRes;
import com.plonit.plonitservice.api.member.service.MemberService;
import com.plonit.plonitservice.api.member.service.dto.UpdateMemberDto;
import com.plonit.plonitservice.api.member.service.dto.UpdateVolunteerInfoDto;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.exception.ErrorCode;
import com.plonit.plonitservice.common.util.RequestUtils;
import com.plonit.plonitservice.domain.badge.repository.MemberBadgeQueryRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberQueryRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewQueryRepository;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import com.plonit.plonitservice.domain.region.Dong;
import com.plonit.plonitservice.domain.region.repository.DongRepository;
import com.plonit.plonitservice.domain.region.repository.RegionQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.plonit.plonitservice.common.exception.ErrorCode.*;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.START;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    private final AwsS3Uploader awsS3Uploader;
    private final DongRepository dongRepository;
    private final RegionQueryRepository regionQueryRepository;
    private final PloggingFeignClient ploggingFeignClient;
    private final CrewMemberQueryRepository crewMemberQueryRepository;
    private final MemberBadgeQueryRepository memberBadgeQueryRepository;
    private final CrewQueryRepository crewQueryRepository;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Transactional
    public FindMemberRes updateMember(UpdateMemberDto updateMemberDto) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        Member member = memberRepository.findById(updateMemberDto.getMemberId())
                .orElseThrow(() -> new CustomException(USER_BAD_REQUEST));

        if (updateMemberDto.getProfileImage() != null) {
            try {
                String profileUrl = awsS3Uploader.uploadFile(updateMemberDto.getProfileImage(), "member/profileImage");
                member.setProfileImage(profileUrl);
            } catch (IOException e) {
                throw new CustomException(INVALID_FIELDS_REQUEST);
            }
        }

        Dong dong = regionQueryRepository.findDongFetchJoin(updateMemberDto.getDongCode())
                .orElseThrow(() -> new CustomException(REGION_NOT_FOUND));

        member.changeInfo(updateMemberDto, dong);


        log.info(logCurrent(getClassName(), getMethodName(), END));
        return FindMemberRes.of(member);
    }

    @Override
    public FindMemberInfoRes findMemberInfo() {
        Long memberId = RequestUtils.getMemberId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(USER_BAD_REQUEST));

        Integer ploggingCount = ploggingFeignClient.countPlogging(RequestUtils.getToken()).getResultBody();
        Integer crewCount = crewMemberQueryRepository.countByCrewMemberByMemberId(memberId);
        Integer badgeCount = memberBadgeQueryRepository.countByMemberBadgeByMemberId(memberId);

        return FindMemberInfoRes.of(member, ploggingCount, crewCount, badgeCount);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FindCrewInfoRes> findCrewInfo() {
        Long memberId = RequestUtils.getMemberId();

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        HashMap<Long, Long> crewPloggingCount = circuitBreaker.run(
                () -> ploggingFeignClient.countCrewPlogging(RequestUtils.getToken()).getResultBody(),
                throwable -> new HashMap<>()
        );

//        HashMap<Long, Long> crewPloggingCount = ploggingFeignClient.countCrewPlogging(RequestUtils.getToken()).getResultBody();

        List<FindCrewInfoRes> crewInfoList =  crewQueryRepository.findCrewsByMemberId(memberId);
        crewInfoList.stream()
                .forEach(item -> item.setFloggingCount(crewPloggingCount.getOrDefault(item.getId(), 0L)));

        return crewInfoList;
    }

    @Override
    public void updateVolunteerInfo(UpdateVolunteerInfoDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_BAD_REQUEST));

        member.updateVolunteerInfo(dto);
    }
}
