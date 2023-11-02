package com.plonit.plonitservice.api.crewping.service.dto;

import com.plonit.plonitservice.api.crewping.controller.request.SaveCrewpingReq;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crewping.Crewping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@Builder(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class SaveCrewpingDto {

    private long memberKey;

    private String name;

    private MultipartFile crewpingImage;

    private String startDate;

    private String endDate;

    private Integer maxPeople;

    private String place;

    private String introduce;

    private String notice;


    public static SaveCrewpingDto of(Long memberKey, SaveCrewpingReq saveCrewpingReq) {
        return SaveCrewpingDto.builder()
                .memberKey(memberKey)
                .name(saveCrewpingReq.getName())
                .crewpingImage(saveCrewpingReq.getCrewpingImage())
                .startDate(saveCrewpingReq.getStartDate())
                .endDate(saveCrewpingReq.getEndDate())
                .maxPeople(saveCrewpingReq.getMaxPeople())
                .place(saveCrewpingReq.getPlace())
                .introduce(saveCrewpingReq.getIntroduce())
                .notice(saveCrewpingReq.getNotice())
                .build();
    }

    public static Crewping toEntity(SaveCrewpingDto dto, String crewpingImageUrl) {
        return Crewping.builder()
                .crew()
                .crewName()
                .name(dto.getName())
                .crewpingImage(crewpingImageUrl)
                .startDate(startDate)
                .endDate(endDate)
                .cntPeople(1)
                .maxPeople(dto.getMaxPeople())
                .place(dto.getPlace())
                .introduce(dto.getIntroduce())
                .notice(dto.getNotice())
                .activeTime(0l)
                .build();
    }
}
