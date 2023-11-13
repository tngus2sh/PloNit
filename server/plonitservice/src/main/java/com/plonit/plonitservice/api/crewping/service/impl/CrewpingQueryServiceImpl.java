package com.plonit.plonitservice.api.crewping.service.impl;

import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingMembersByMasterRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingMembersRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingsRes;
import com.plonit.plonitservice.api.crewping.service.CrewpingQueryService;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.exception.ErrorCode;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberQueryRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewRepository;
import com.plonit.plonitservice.domain.crewping.Crewping;
import com.plonit.plonitservice.domain.crewping.CrewpingMember;
import com.plonit.plonitservice.domain.crewping.repository.CrewpingMemberQueryRepository;
import com.plonit.plonitservice.domain.crewping.repository.CrewpingMemberRepository;
import com.plonit.plonitservice.domain.crewping.repository.CrewpingQueryRepository;
import com.plonit.plonitservice.domain.crewping.repository.CrewpingRepository;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CrewpingQueryServiceImpl implements CrewpingQueryService {

    private final CrewpingRepository crewpingRepository;
    private final CrewpingQueryRepository crewpingQueryRepository;
    private final CrewpingMemberRepository crewpingMemberRepository;
    private final CrewpingMemberQueryRepository crewpingMemberQueryRepository;
    private final CrewRepository crewRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final CrewMemberQueryRepository crewMemberQueryRepository;
    private final MemberRepository memberRepository;


    @Override
    public List<FindCrewpingsRes> findCrewpings(Long memberId, Long crewId) {
        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new CustomException(ErrorCode.CREW_NOT_FOUND));

        crewMemberRepository.findCrewMemberByJoinFetch(memberId, crewId)
                .orElseThrow(() -> new CustomException(ErrorCode.CREWPING_BAD_REQUEST));

        List<FindCrewpingsRes> result = crewpingQueryRepository.findCrewpings(crewId);

        return result;
    }

    @Override
    public FindCrewpingRes findCrewping(Long memberId, Long crewpingId) {
        Crewping crewping = crewpingRepository.findById(crewpingId)
                .orElseThrow(() -> new CustomException(ErrorCode.CREWPING_NOT_FOUND));

        crewMemberRepository.findCrewMemberByJoinFetch(memberId, crewping.getCrew().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.CREWPING_BAD_REQUEST));

        CrewpingMember masterCrewpingMember = crewpingMemberRepository.findMasterCrewpingMemberWitMemberJoinFetch(crewpingId)
                .orElseThrow(() -> new CustomException(ErrorCode.CREWPINGMEMBER_NOT_FOUND));

        FindCrewpingRes result = FindCrewpingRes.of(crewping, masterCrewpingMember, crewpingMemberQueryRepository.isCrewpingMember(memberId, crewpingId));

        return result;
    }

    @Override
    public List<FindCrewpingMembersByMasterRes> findCrewpingMembersByMaster(Long memberId, Long crewpingId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Crewping crewping = crewpingRepository.findById(crewpingId)
                .orElseThrow(() -> new CustomException(ErrorCode.CREWPING_NOT_FOUND));

        CrewpingMember masterCrewpingMember = crewpingMemberRepository.findMasterCrewpingMemberWitMemberJoinFetch(crewpingId).get();
        if(masterCrewpingMember.getMember().getId() != memberId) {
            throw new CustomException(ErrorCode.CREWPING_BAD_REQUEST);
        }

        List<FindCrewpingMembersByMasterRes> result = crewpingMemberQueryRepository.findCrewpingMembersByMaster(crewpingId);

        return result;
    }

    @Override
    public List<FindCrewpingMembersRes> findCrewpingMembers(Long memberId, Long crewpingId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Crewping crewping = crewpingRepository.findById(crewpingId)
                .orElseThrow(() -> new CustomException(ErrorCode.CREWPING_NOT_FOUND));

        if(!crewMemberQueryRepository.isValidCrewMember(memberId, crewping.getCrew().getId(), null)) {
            throw new CustomException(ErrorCode.CREWPING_BAD_REQUEST);
        }

        List<FindCrewpingMembersRes> result = crewpingMemberQueryRepository.findCrewpingMembers(crewpingId);

        return result;
    }
}
