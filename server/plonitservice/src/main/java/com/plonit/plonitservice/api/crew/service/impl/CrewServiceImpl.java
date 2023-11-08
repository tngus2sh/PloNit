package com.plonit.plonitservice.api.crew.service.impl;

import com.plonit.plonitservice.api.crew.service.CrewService;
import com.plonit.plonitservice.api.crew.service.dto.ApproveCrewDto;
import com.plonit.plonitservice.api.crew.service.dto.SaveCrewDto;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.CrewMember;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberQueryRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewQueryRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewRepository;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import com.plonit.plonitservice.domain.region.Dong;
import com.plonit.plonitservice.domain.region.Gugun;
import com.plonit.plonitservice.domain.region.repository.RegionQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.plonit.plonitservice.common.exception.ErrorCode.*;
import static com.plonit.plonitservice.common.util.LogCurrent.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrewServiceImpl implements CrewService {

    private final CrewRepository crewRepository;
    private final CrewQueryRepository crewQueryRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final CrewMemberQueryRepository crewMemberQueryRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    private final AwsS3Uploader awsS3Uploader;
    private final RegionQueryRepository regionQueryRepository;

    @Override
    @Transactional // 크루 생성
    public void saveCrew(SaveCrewDto saveCrewDTO) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        Member member = memberRepository.findById(saveCrewDTO.getMemberKey())
                .orElseThrow(() -> new CustomException(USER_BAD_REQUEST));

        String crewImageUrl = null;
        if (saveCrewDTO.getCrewImage() != null) {
            try {
                crewImageUrl = awsS3Uploader.uploadFile(saveCrewDTO.getCrewImage(), "crew/crewImage");
            } catch (IOException e) {
                throw new CustomException(INVALID_FIELDS_REQUEST);
            }
        }

        Dong dong = regionQueryRepository.findDongFetchJoin(saveCrewDTO.getDongCode())
                .orElseThrow(() -> new CustomException(REGION_NOT_FOUND));

        Crew crew = crewRepository.save(SaveCrewDto.toEntity(saveCrewDTO, crewImageUrl, dong));
        CrewMember crewMember = CrewMember.toEntity(crew, member);

        crewMemberRepository.save(crewMember);
        log.info(logCurrent(getClassName(), getMethodName(), END));
    }

    @Override
    @Transactional // 크루 가입 요청
    public void joinCrewMember(Long memberId, Long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new CustomException(CREW_NOT_FOUND));

        if (crewMemberQueryRepository.isValidCrewMember(memberId, crewId, false))
            throw new CustomException(CREW_ALREADY_JOIN);

        CrewMember crewMember = CrewMember.memberToEntity(crew, member);

        crewMemberRepository.save(crewMember);
        log.info(logCurrent(getClassName(), getMethodName(), END));
    }

    @Override
    @Transactional // 크루 승인
    public void approveCrewMember(ApproveCrewDto approveCrewDto) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        // 크루장인지 확인
        if (!crewMemberQueryRepository.isValidCrewMember(approveCrewDto.getMemberKey(), approveCrewDto.getCrewId(), true))
            throw new CustomException(CREW_MASTER_NOT_FORBIDDEN);

        // 크루원 승인이 대기 중인지 확인
        CrewMember crewMember = crewMemberQueryRepository.findByMemberIdAndCrewId(approveCrewDto.getCrewMemberId(), approveCrewDto.getCrewId())
                .orElseThrow(() -> new CustomException(CREW_NOT_FOUND));

        // 승인 -> change , 거절 -> delete
        if (approveCrewDto.getStatus()) crewMember.changeIsCrewMember();
        else crewMemberRepository.delete(crewMember);

        log.info(logCurrent(getClassName(), getMethodName(), END));
    }

}
