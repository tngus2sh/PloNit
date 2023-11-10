package com.plonit.ploggingservice.api.plogging.controller.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
public class ImagePloggingReq {
    
    @NotNull(message = "플로깅 id는 필수 값입니다.")
    private Long id;
    
    private MultipartFile image;
    
}
