package com.plonit.plonitservice.api.crew.service.dto;

import com.plonit.plonitservice.api.crew.controller.request.SaveCrewReq;
import com.plonit.plonitservice.domain.crew.Crew;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class SaveCrewDto {

    private long memberKey;

    private String name;

    private MultipartFile crewImage;

    private String introduce;

    private String region;

    public static SaveCrewDto of (Long memberKey, SaveCrewReq saveCrewReq) {
        return SaveCrewDto.builder()
                .memberKey(memberKey)
                .name(saveCrewReq.getName())
                .crewImage(saveCrewReq.getCrewImage())
                .introduce(saveCrewReq.getIntroduce())
                .region(saveCrewReq.getRegion())
                .build();
    }

    public static Crew toEntity (SaveCrewDto saveCrewDTO, String imageUrl) {
        return Crew.builder()
                .name(saveCrewDTO.getName())
                .crewImage(imageUrl)
                .introduce(saveCrewDTO.getIntroduce())
                .region(saveCrewDTO.getRegion())
                .build();
    }

}