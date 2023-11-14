package com.plonit.plonitservice.api.member.service.dto;

import com.plonit.plonitservice.api.member.controller.request.UpdateMemberReq;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UpdateMemberDto {

    private Long memberId;

    private String name;

    private String nickname;

    private MultipartFile profileImage;

    private Boolean gender;

    private String birth;

    private Long dongCode;

    private String height;

    private String weight;

    private String id1365;

    public static UpdateMemberDto of (Long memberKey, UpdateMemberReq updateMemberReq) {
        return UpdateMemberDto.builder()
                .memberId(memberKey)
                .name(updateMemberReq.getName())
                .nickname(updateMemberReq.getNickname())
                .profileImage(updateMemberReq.getProfileImage())
                .gender(Boolean.parseBoolean(updateMemberReq.getGender()))
                .birth(updateMemberReq.getBirth())
                .dongCode(Long.parseLong(updateMemberReq.getDongCode()))
                .height(updateMemberReq.getHeight()) // float 변경 예정
                .weight(updateMemberReq.getWeight())
                .id1365(updateMemberReq.getId1365())
                .build();
    }

}