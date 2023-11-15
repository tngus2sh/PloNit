package com.plonit.ploggingservice.api.plogging.service.dto;

import com.plonit.ploggingservice.api.plogging.controller.request.HelpPloggingReq;
import com.plonit.ploggingservice.domain.plogging.PloggingHelp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpPloggingDto {

    private Long memberKey;
    
    private Double latitude;

    private Double longitude;

    private MultipartFile image;

    private String context;

    public static HelpPloggingDto of(HelpPloggingReq req, Long memberKey) {
        return HelpPloggingDto.builder()
                .memberKey(memberKey)
                .latitude(req.getLatitude())
                .longitude(req.getLongitude())
                .image(req.getImage())
                .context(req.getContext())
                .build();
    }

    public static PloggingHelp toEntity(HelpPloggingDto dto, Long gugunCode, String place, String imageUrl) {
        return PloggingHelp.builder()
                .memberKey(dto.getMemberKey())
                .gugunCode(gugunCode)
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .place(place)
                .context(dto.getContext())
                .image(imageUrl)
                .isActive(true)
                .build();
    }
}
