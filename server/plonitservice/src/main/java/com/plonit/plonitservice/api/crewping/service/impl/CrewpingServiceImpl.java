package com.plonit.plonitservice.api.crewping.service.impl;

import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingsRes;
import com.plonit.plonitservice.api.crewping.service.dto.SaveCrewpingDto;
import com.plonit.plonitservice.api.crewping.service.CrewpingService;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.exception.ErrorCode;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.CrewMember;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberQueryRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewRepository;
import com.plonit.plonitservice.domain.crewping.Crewping;
import com.plonit.plonitservice.domain.crewping.CrewpingMember;
import com.plonit.plonitservice.domain.crewping.repository.CrewpingMemberRepository;
import com.plonit.plonitservice.domain.crewping.repository.CrewpingQueryRepository;
import com.plonit.plonitservice.domain.crewping.repository.CrewpingRepository;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.plonit.plonitservice.common.exception.ErrorCode.*;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.START;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CrewpingServiceImpl implements CrewpingService {

    private final CrewpingRepository crewpingRepository;
    private final CrewpingQueryRepository crewpingQueryRepository;
    private final CrewpingMemberRepository crewpingMemberRepository;
    private final MemberRepository memberRepository;
    private final CrewRepository crewRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final CrewMemberQueryRepository crewMemberQueryRepository;
    private final AwsS3Uploader awsS3Uploader;

    @Override
    public void saveCrewping(SaveCrewpingDto dto) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Member member = memberRepository.findById(dto.getMemberKey())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_BAD_REQUEST));

        // TODO: N + 1 문제 해결 필요
        Crew crew = crewRepository.findById(dto.getCrewId())
                .orElseThrow(() -> new CustomException(ErrorCode.CREW_NOT_FOUND));

        crewMemberRepository.findCrewMemberWithCrewByFetch(dto.getMemberKey(), dto.getCrewId())
                .orElseThrow(() -> new CustomException(CREWPING_BAD_REQUEST));

        String crewpingImageUrl = null;
        if(dto.getCrewpingImage() != null) {
            try {
                crewpingImageUrl = awsS3Uploader.uploadFile(dto.getCrewpingImage(), "crewping/crewpingImage");
            } catch (IOException e) {
                throw new CustomException(INVALID_FIELDS_REQUEST);
            }
        }

        Crewping crewping = crewpingRepository.save(dto.toEntity(crew, crewpingImageUrl));
        CrewpingMember crewpingMember =crewpingMemberRepository.save(CrewpingMember.of(member, crewping, true));
    }

    @Override
    public List<FindCrewpingsRes> findCrewpings(Long memberId, Long crewId) {
        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new CustomException(CREW_NOT_FOUND));

        crewMemberRepository.findCrewMemberWithCrewByFetch(memberId, crewId)
                .orElseThrow(() -> new CustomException(CREWPING_BAD_REQUEST));

        List<FindCrewpingsRes> result = crewpingQueryRepository.findCrewpings(crewId);

        return result;
    }

    @Override
    public FindCrewpingRes findCrewping(Long memberId, Long crewpingId) {
        Crewping crewping = crewpingRepository.findById(crewpingId)
                .orElseThrow(() -> new CustomException(CREWPING_NOT_FOUND));

        Optional<CrewMember> crewMember = crewMemberRepository.findCrewMemberWithCrewByFetch(memberId, crewping.getCrew().getId());
        if(crewMember.isEmpty()) {
            throw new CustomException(CREWPING_BAD_REQUEST);
        }

        CrewpingMember masterCrewpingMember = crewpingMemberRepository.findMasterCrewpingMemberWitMemberJoinFetch(crewpingId)
                .orElseThrow(() -> new CustomException(CREWPINGMEMBER_NOT_FOUND));

        FindCrewpingRes result = FindCrewpingRes.of(crewping, masterCrewpingMember);

        return result;
    }
}
