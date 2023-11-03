package com.plonit.ploggingservice.api.plogging.controller.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class ImagePloggingReq {
    
    @NotBlank(message = "플로깅 id는 필수 값입니다.")
    private Long id;
    
    private MultipartFile image;
    
}
