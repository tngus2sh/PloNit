package com.plonit.plonitservice.api.crew.service.impl;

import com.plonit.plonitservice.api.crew.service.CrewService;
import com.plonit.plonitservice.api.crew.service.dto.ApproveCrewDto;
import com.plonit.plonitservice.api.crew.service.dto.SaveCrewDto;
import com.plonit.plonitservice.api.crew.service.dto.SaveCrewNoticeDto;
import com.plonit.plonitservice.api.crew.service.dto.UpdateCrewNoticeDto;
import com.plonit.plonitservice.api.fcm.controller.request.FCMReq;
import com.plonit.plonitservice.api.fcm.service.FCMService;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.util.RequestUtils;
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
    private final FCMService fcmService;

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

        // 멤버 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 크루 조회
        Crew crew = crewRepository.findById(crewId)
                .orElseThrow(() -> new CustomException(CREW_NOT_FOUND));

        // 크루원 조회
        if (crewMemberQueryRepository.isValidCrewMember(memberId, crewId, false))
            throw new CustomException(CREW_ALREADY_JOIN);

        // 크루원 저장
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

        // 크루원 확인
        CrewMember crewMember = crewMemberQueryRepository.findByMemberIdAndCrewId(approveCrewDto.getCrewMemberId(), approveCrewDto.getCrewId())
                .orElseThrow(() -> new CustomException(CREW_MEMBER_NOT_FOUND));

        // 승인 대기 확인
        if(crewMember.getIsCrewMember())
            throw new CustomException(CREW_ALREADY_JOIN);

        // 승인 -> change , 거절 -> delete
        if (approveCrewDto.getStatus()) {
            crewMember.changeIsCrewMember();

            // fcm 알림
            fcmService.sendNotification(FCMReq.builder()
                    .targetMemberId(approveCrewDto.getCrewMemberId())
                    .title("CREW_APRV")
                    .body(crewMember.getCrew().getName() + " 크루 승인이 완료되었습니다.")
                    .build());
        } else {
            crewMemberRepository.delete(crewMember);

            // fcm 알림
            fcmService.sendNotification(FCMReq.builder()
                    .targetMemberId(approveCrewDto.getCrewMemberId())
                    .title("CREW_APRV")
                    .body(crewMember.getCrew().getName() + " 크루 승인이 거절되었습니다.")
                    .build());
        }
        log.info(logCurrent(getClassName(), getMethodName(), END));
    }

    @Override
    @Transactional // 공지사항 등록
    public void saveCrewNotice(SaveCrewNoticeDto saveCrewNoticeDto) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberId = RequestUtils.getMemberId();

        // 크루장인지 확인
        if (!crewMemberQueryRepository.isValidCrewMember(memberId, saveCrewNoticeDto.getCrewId(), true))
            throw new CustomException(CREW_MASTER_NOT_FORBIDDEN);


        Crew crew = crewRepository.findById(saveCrewNoticeDto.getCrewId())
                .orElseThrow(() -> new CustomException(CREW_NOT_FOUND));

        // 공지사항 등록
        crew.changeNotice(saveCrewNoticeDto.getContent());

        log.info(logCurrent(getClassName(), getMethodName(), END));
    }

    @Override
    @Transactional // 크루 탈퇴
    public void quitCrew(Long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberId = RequestUtils.getMemberId();

        // 크루 확인
        if(!crewQueryRepository.isValidCrew(crewId))
            throw new CustomException(CREW_NOT_FOUND);

        // 크루장인지 확인 -> 탈퇴 X
        if (crewMemberQueryRepository.isValidCrewMember(memberId, crewId, true))
            throw new CustomException(CREW_MASTER_NOT_QUIT);

        // 크루원 확인
        CrewMember crewMember = crewMemberQueryRepository.findByMemberIdAndCrewId(memberId, crewId)
                .orElseThrow(() -> new CustomException(CREW_MEMBER_NOT_FOUND));

        // 승인 대기 확인
        if(!crewMember.getIsCrewMember())
            throw new CustomException(CREW_WAITING_FORBIDDEN);

        crewMemberRepository.delete(crewMember);

        log.info(logCurrent(getClassName(), getMethodName(), END));
    }

    @Override
    @Transactional // 크루 강퇴
    public void kickOutCrew(Long crewMemberId, Long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberId = RequestUtils.getMemberId();

        // 크루 확인
        if(!crewQueryRepository.isValidCrew(crewId))
            throw new CustomException(CREW_NOT_FOUND);

        // (로그인한 유저) 크루장인지 확인
        if (!crewMemberQueryRepository.isValidCrewMember(memberId, crewId, true))
            throw new CustomException(CREW_MASTER_NOT_FORBIDDEN);

        // 크루원 확인
        CrewMember crewMember = crewMemberQueryRepository.findByMemberIdAndCrewId(crewMemberId, crewId)
                .orElseThrow(() -> new CustomException(CREW_MEMBER_NOT_FOUND));

        // 승인 대기 확인
        if(!crewMember.getIsCrewMember())
            throw new CustomException(CREW_WAITING_FORBIDDEN);

        crewMemberRepository.delete(crewMember);

        // fcm 알림
        fcmService.sendNotification(FCMReq.builder()
                .targetMemberId(crewMemberId)
                .title("CREW_DROP")
                .body(crewMember.getCrew().getName() + " 크루 강퇴되었습니다.")
                .build());

        log.info(logCurrent(getClassName(), getMethodName(), END));
    }

}
