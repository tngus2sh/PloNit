package com.plonit.plonitservice.api.crew.service.dto;

import com.plonit.plonitservice.api.crew.controller.request.SaveCrewReq;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.region.Dong;
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

    private Long dongCode;

    public static SaveCrewDto of (Long memberKey, SaveCrewReq saveCrewReq) {
        return SaveCrewDto.builder()
                .memberKey(memberKey)
                .name(saveCrewReq.getName())
                .crewImage(saveCrewReq.getCrewImage())
                .introduce(saveCrewReq.getIntroduce())
                .dongCode(Long.parseLong(saveCrewReq.getDongCode()))
                .build();
    }

    public static Crew toEntity (SaveCrewDto saveCrewDTO, String imageUrl, Dong dong) {
        StringBuilder sb = new StringBuilder();
        sb.append(dong.getGugun().getSido().getName() + " " +
                dong.getGugun().getName());

        return Crew.builder()
                .name(saveCrewDTO.getName())
                .crewImage(imageUrl)
                .introduce(saveCrewDTO.getIntroduce())
                .gugunCode(dong.getGugun().getCode())
                .region(sb.toString())
                .build();
    }

}