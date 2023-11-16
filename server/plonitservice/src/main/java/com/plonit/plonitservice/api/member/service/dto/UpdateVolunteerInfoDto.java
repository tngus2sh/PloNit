package com.plonit.plonitservice.api.member.service.dto;

import com.plonit.plonitservice.api.member.controller.request.UpdateVolunteerInfoReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@Builder(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class UpdateVolunteerInfoDto {

    private Long memberId;
    private String name;
    private String phoneNumber;
    private String id1365;
    private String email;
    private String birth;


    public static UpdateVolunteerInfoDto of(UpdateVolunteerInfoReq request) {
        return UpdateVolunteerInfoDto.builder()
                .memberId(request.getMemberKey())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .id1365(request.getId1365())
                .email(request.getEmail())
                .birth(request.getBirth())
                .build();
    }
}
