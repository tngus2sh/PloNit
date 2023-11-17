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
import java.time.format.DateTimeFormatter;

import static com.plonit.plonitservice.common.enums.Status.ACTIVE;
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


    public static SaveCrewpingDto of(Long memberKey, SaveCrewpingReq request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return SaveCrewpingDto.builder()
                .memberKey(memberKey)
                .crewId(request.getCrewId())
                .name(request.getName())
                .crewpingImage(request.getCrewpingImage())
                .startDate(LocalDateTime.parse(request.getStartDate(), formatter))
                .endDate(LocalDateTime.parse(request.getEndDate(), formatter))
                .maxPeople(request.getMaxPeople())
                .place(request.getPlace())
                .introduce(request.getIntroduce())
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
                .activeTime(0l)
                .status(ACTIVE)
                .build();
    }
}
