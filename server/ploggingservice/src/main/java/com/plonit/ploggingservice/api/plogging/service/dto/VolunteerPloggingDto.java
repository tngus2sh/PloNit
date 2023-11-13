package com.plonit.ploggingservice.api.plogging.service.dto;

import com.plonit.ploggingservice.api.plogging.controller.request.VolunteerPloggingReq;
import com.plonit.ploggingservice.common.enums.Finished;
import com.plonit.ploggingservice.common.enums.VolunteerStatus;
import com.plonit.ploggingservice.domain.plogging.Plogging;
import com.plonit.ploggingservice.domain.plogging.Volunteer;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VolunteerPloggingDto {

    private Long memberKey;

    private Long ploggingId;

    private String name;

    private String phoneNumber;

    private String id1365;

    private String email;

    private String birth;

    public static VolunteerPloggingDto of(VolunteerPloggingReq req, Long memberKey) {
        return VolunteerPloggingDto.builder()
                .memberKey(memberKey)
                .ploggingId(req.getPloggingId())
                .name(req.getName())
                .phoneNumber(req.getPhoneNumber())
                .id1365(req.getId1365())
                .email(req.getEmail())
                .birth(req.getBirth())
                .build();
    }

    public static Volunteer toEntity(Plogging plogging) {
        return Volunteer.builder()
                .plogging(plogging)
                .status(VolunteerStatus.APPROVE)
                .build();
    }

}
