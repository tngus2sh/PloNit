package com.plonit.plonitservice.api.badge.service.dto;

import com.plonit.plonitservice.api.badge.controller.request.BadgeReq;
import com.plonit.plonitservice.common.enums.BadgeStatus;
import com.plonit.plonitservice.domain.badge.Badge;
import com.plonit.plonitservice.domain.badge.BadgeCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadgeDto {
    private String name;

    private MultipartFile image;

    private Boolean type;

    private BadgeStatus status;

    private Integer value;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public static BadgeDto of(BadgeReq req) {
        return BadgeDto.builder()
                .name(req.getName())
                .image(req.getImage())
                .type(req.getType())
                .status(BadgeStatus.valueOf(req.getStatus()))
                .value(req.getValue())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .build();
    }
    
    public static BadgeCondition toEntity(BadgeDto badgeDto) {
        return BadgeCondition.builder()
                .status(badgeDto.getStatus())
                .startTime(badgeDto.getStartDate())
                .endTime(badgeDto.getEndDate())
                .value(badgeDto.getValue())
                .build();        
    }

    public static Badge toEntity(BadgeDto badgeDto, BadgeCondition badgeCondition, String badgeImageUrl) {
        return Badge.builder()
                .badgeCondition(badgeCondition)
                .name(badgeDto.getName())
                .image(badgeImageUrl)
                .type(badgeDto.getType())
                .build();
    }

}
