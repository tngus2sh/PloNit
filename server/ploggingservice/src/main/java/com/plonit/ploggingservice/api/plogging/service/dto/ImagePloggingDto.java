package com.plonit.ploggingservice.api.plogging.service.dto;

import com.plonit.ploggingservice.api.plogging.controller.request.ImagePloggingReq;
import com.plonit.ploggingservice.domain.plogging.PloggingPicture;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
@Builder
public class ImagePloggingDto {

    private Long id;

    private MultipartFile image;

    public static ImagePloggingDto of(ImagePloggingReq req) {
        return ImagePloggingDto.builder()
                .id(req.getId())
                .image(req.getImage())
                .build();
    }

    public static PloggingPicture toEntity(Long id, String image) {
        return PloggingPicture.builder()
                .id(id)
                .image(image)
                .build();
    }
    
}
