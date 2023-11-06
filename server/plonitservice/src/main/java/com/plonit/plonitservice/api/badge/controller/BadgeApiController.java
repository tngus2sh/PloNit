package com.plonit.plonitservice.api.badge.controller;

import com.plonit.plonitservice.api.badge.controller.request.BadgeReq;
import com.plonit.plonitservice.api.badge.controller.request.CrewBadgeReq;
import com.plonit.plonitservice.api.badge.controller.request.MembersBadgeReq;
import com.plonit.plonitservice.api.badge.service.BadgeService;
import com.plonit.plonitservice.api.badge.service.dto.BadgeDto;
import com.plonit.plonitservice.common.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Badge API Controller", description = "Badge API Document")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/plonit-service/v1/badge")
public class BadgeApiController {
    
    private final BadgeService badgeService;
    
    @Operation(summary = "[관리자용] 배지 설정", description = "배지를 설정합니다.")
    @PostMapping("/setting")
    public CustomApiResponse<Void> saveBadge(
            @RequestBody List<BadgeReq> reqs
            ) {
        // 배지 설정
        List<BadgeDto> badgeDtos = new ArrayList<>();
        for (BadgeReq req : reqs) {
            badgeDtos.add(BadgeDto.of(req));
        }
        badgeService.saveBadge(badgeDtos);
        
        return CustomApiResponse.ok(null);
    }

    @Operation(summary = "[관리자용] 개인 배지 부여", description = "개인 배지를 부여합니다.")
    @PostMapping("/member-grant")
    public CustomApiResponse<Void> saveBadgeByIndividual(
            @RequestBody List<MembersBadgeReq> reqs
    ) {
        // TODO: 2023-10-30 개인 배지 부여 
        
        return null;
    }
    
    
    @Operation(summary = "[관리자용] 크루 배지 부여", description = "크루 배지를 부여합니다.")
    @PostMapping("/crew-grant")
    public CustomApiResponse<Void> saveBadgeByCrew(
            @RequestBody List<CrewBadgeReq> reqs
    ) {
        // TODO: 2023-10-30 크루 배지 부여 
        
        return null;
    } 
    
}
