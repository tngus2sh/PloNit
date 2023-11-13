package com.plonit.ploggingservice.api.excel.service.dto;

import com.plonit.ploggingservice.api.excel.controller.response.VolunteerMemberInfoRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder()
public class PloggingDto {

    private Long memberId;
    private String id1365;
    private String name;
    private String birth;
    private Long time;
    private Double distance;
    private String startImage;
    private String middleImage;
    private String endImage;


    public void setVolunteerInfo(VolunteerMemberInfoRes volunteerInfo) {
        this.id1365 = volunteerInfo.getId1365();
        this.name = volunteerInfo.getName();
        this.birth = volunteerInfo.getBirth();
    }
}
