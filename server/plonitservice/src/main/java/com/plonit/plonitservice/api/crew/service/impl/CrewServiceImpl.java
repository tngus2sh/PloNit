package com.plonit.plonitservice.api.crew.service.impl;

import com.plonit.plonitservice.api.crew.service.CrewService;
import com.plonit.plonitservice.api.crew.service.DTO.SaveCrewDTO;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.exception.ErrorCode;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.CrewMember;
import com.plonit.plonitservice.domain.crew.repository.CrewMemberRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewQueryRepository;
import com.plonit.plonitservice.domain.crew.repository.CrewRepository;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;
import static com.plonit.plonitservice.common.util.LogCurrent.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrewServiceImpl implements CrewService{

    private final CrewRepository crewRepository;
    private final CrewQueryRepository crewQueryRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    private final AwsS3Uploader awsS3Uploader;

    @Transactional // 크루 생성
    public void saveCrew(SaveCrewDTO saveCrewDTO) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        Member member = memberRepository.findById(saveCrewDTO.getMemberKey())
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_BAD_REQUEST));

        String crewImageUrl = null;
        if (saveCrewDTO.getCrewImage() != null) {
            try {
                crewImageUrl = awsS3Uploader.uploadFile(saveCrewDTO.getCrewImage(), "crew/crewImage");
            } catch (IOException e) {
                throw new CustomException(INVALID_FIELDS_REQUEST);
            }
        }
        Crew crew = crewRepository.save(SaveCrewDTO.toEntity(saveCrewDTO, crewImageUrl));

        CrewMember crewMember = CrewMember.builder()
                .member(member)
                .isCrewMaster(true)
                .isCrewMember(true)
                .crew(crew)
                .build();

        crewMemberRepository.save(crewMember);
        log.info(logCurrent(getClassName(), getMethodName(), END));
    }

}
