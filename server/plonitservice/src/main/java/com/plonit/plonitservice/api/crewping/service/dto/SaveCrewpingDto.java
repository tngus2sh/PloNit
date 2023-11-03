package com.plonit.plonitservice.api.crewping.service.dto;

import com.plonit.plonitservice.api.crewping.controller.request.SaveCrewpingReq;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crewping.Crewping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@Builder(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class SaveCrewpingDto {

    private Long memberKey;

    private Long crewId;

    private String name;

    private MultipartFile crewpingImage;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

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

    public Crewping toEntity(Crew crew, String crewpingImageUrl) {
        return Crewping.builder()
                .crew(crew)
                .crewName(crew.getName())
                .name(name)
                .crewpingImage(crewpingImageUrl)
                .startDate(startDate)
                .endDate(endDate)
                .cntPeople(1)
                .maxPeople(maxPeople)
                .place(place)
                .introduce(introduce)
                .notice(notice)
                .activeTime(0l)
                .build();
    }
}
