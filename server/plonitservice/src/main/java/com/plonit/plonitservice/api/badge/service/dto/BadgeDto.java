package com.plonit.plonitservice.api.badge.service.dto;

import com.plonit.plonitservice.api.badge.controller.request.BadgeReq;
import com.plonit.plonitservice.common.enums.BadgeStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
public class BadgeDto {
    private String name;

    private String image;

    private Boolean type;

    private BadgeStatus status;

    private Integer value;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
