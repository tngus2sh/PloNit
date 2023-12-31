package com.plonit.ploggingservice.api.plogging.service.dto;

import com.plonit.ploggingservice.api.plogging.controller.request.ImagePloggingReq;
import com.plonit.ploggingservice.domain.plogging.Plogging;
import com.plonit.ploggingservice.domain.plogging.PloggingPicture;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ImagePloggingDto {

    private Long id;

    private MultipartFile image;

    public static ImagePloggingDto of(ImagePloggingReq req) {
        return ImagePloggingDto.builder()
                .id(req.getId())
                .image(req.getImage())
                .build();
    }

    public static PloggingPicture toEntity(Long id, Plogging plogging, String image) {
        return PloggingPicture.builder()
                .id(id)
                .plogging(plogging)
                .image(image)
                .build();
    }
    
}
