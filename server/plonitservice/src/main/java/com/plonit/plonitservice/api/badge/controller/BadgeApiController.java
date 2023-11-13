package com.plonit.plonitservice.api.badge.controller;

import com.plonit.plonitservice.api.badge.controller.request.GrantMemberBadgeReq;
import com.plonit.plonitservice.api.badge.service.BadgeService;
import com.plonit.plonitservice.common.CustomApiResponse;
import com.plonit.plonitservice.common.util.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Badge API Controller", description = "Badge API Document")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/plonit-service/api/badge")
public class BadgeApiController {

    private final BadgeService badgeService;


    @Operation(summary = "[관리자용] 개인 배지 부여 여부 확인", description = "개인 배지를 부여할 수 있는지 확인한 후 부여한다.")
    @PostMapping("/member")
    public CustomApiResponse<Void> grantMemberBadge(
            @RequestBody GrantMemberBadgeReq grantMemberBadgeReq,
            HttpServletRequest servletRequest
    ) {
        RequestUtils.getMemberKey(servletRequest);

        return null;
    }

}
