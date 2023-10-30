package com.plonit.plonitservice.api.member.service.impl;

import com.plonit.plonitservice.api.member.controller.request.UpdateMemberReq;
import com.plonit.plonitservice.api.member.controller.response.FindMemberRes;
import com.plonit.plonitservice.api.member.service.MemberService;
import com.plonit.plonitservice.common.AwsS3Uploader;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;
import static com.plonit.plonitservice.common.exception.ErrorCode.USER_BAD_REQUEST;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    private final AwsS3Uploader awsS3Uploader;
    @Transactional
    public FindMemberRes updateMember(Long userId, UpdateMemberReq updateMemberReq) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_BAD_REQUEST));

        if (updateMemberReq.getProfileImage() != null) {
            try {
                String profileUrl = awsS3Uploader.uploadFile(updateMemberReq.getProfileImage(), "member/profileImage");
                member.setProfileImage(profileUrl);
            } catch (IOException e) {
                throw new CustomException(INVALID_FIELDS_REQUEST);
            }
        }
        member.changeInfo(updateMemberReq);
        return FindMemberRes.of(member);
    }
}
