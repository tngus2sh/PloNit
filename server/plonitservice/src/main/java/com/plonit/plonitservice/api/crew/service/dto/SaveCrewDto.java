package com.plonit.plonitservice.api.crew.service.dto;

import com.plonit.plonitservice.api.crew.controller.request.SaveCrewReq;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.region.Gugun;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class SaveCrewDto {

    private Long memberKey;

    private String name;

    private MultipartFile crewImage;

    private String introduce;

    private Long gugunCode;

    public static SaveCrewDto of (Long memberKey, SaveCrewReq saveCrewReq) {
        return SaveCrewDto.builder()
                .memberKey(memberKey)
                .name(saveCrewReq.getName())
                .crewImage(saveCrewReq.getCrewImage())
                .introduce(saveCrewReq.getIntroduce())
                .gugunCode(Long.parseLong(saveCrewReq.getGugunCode()))
                .build();
    }

    public static Crew toEntity (SaveCrewDto saveCrewDTO, String imageUrl, Gugun gugun) {
        StringBuilder sb = new StringBuilder();
        sb.append(gugun.getSido().getName() + " " +
                gugun.getName());

        return Crew.builder()
                .name(saveCrewDTO.getName())
                .crewImage(imageUrl)
                .introduce(saveCrewDTO.getIntroduce())
                .gugunCode(saveCrewDTO.getGugunCode())
                .region(sb.toString())
                .build();
    }

}