package com.plonit.plonitservice.api.member.service.impl;

import com.plonit.plonitservice.api.member.controller.response.FindMemberRes;
import com.plonit.plonitservice.api.member.service.MemberService;
import com.plonit.plonitservice.api.member.service.dto.UpdateMemberDto;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import com.plonit.plonitservice.domain.region.Dong;
import com.plonit.plonitservice.domain.region.repository.DongRepository;
import com.plonit.plonitservice.domain.region.repository.RegionQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.plonit.plonitservice.common.exception.ErrorCode.*;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.START;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    private final AwsS3Uploader awsS3Uploader;
    private final DongRepository dongRepository;
    private final RegionQueryRepository regionQueryRepository;
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
}
