package com.plonit.plonitservice.api.crew.service.DTO;

import com.plonit.plonitservice.api.crew.controller.request.SaveCrewReq;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class SaveCrewDTO {

    private long memberKey;

    private String name;

    private MultipartFile crewImage;

    private String introduce;

    private String region;

    public static SaveCrewDTO of (Long memberKey, SaveCrewReq saveCrewReq) {
        return SaveCrewDTO.builder()
                .memberKey(memberKey)
                .name(saveCrewReq.getName())
                .crewImage(saveCrewReq.getCrewImage())
                .introduce(saveCrewReq.getIntroduce())
                .region(saveCrewReq.getRegion())
                .build();
    }

    public static Crew toEntity (SaveCrewDTO saveCrewDTO, String imageUrl) {
        return Crew.builder()
                .name(saveCrewDTO.getName())
                .crewImage(imageUrl)
                .introduce(saveCrewDTO.getIntroduce())
                .region(saveCrewDTO.getRegion())
                .build();
    }

}