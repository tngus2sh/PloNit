package com.plonit.plonitservice.api.badge.controller;

import com.plonit.plonitservice.api.badge.controller.request.GrantCrewRankReq;
import com.plonit.plonitservice.api.badge.controller.request.GrantMemberBadgeReq;
import com.plonit.plonitservice.api.badge.controller.request.GrantMemberRankReq;
import com.plonit.plonitservice.api.badge.controller.response.FindBadgeRes;
import com.plonit.plonitservice.api.badge.service.BadgeService;
import com.plonit.plonitservice.api.badge.service.dto.GrantCrewRankDto;
import com.plonit.plonitservice.api.badge.service.dto.GrantMemberBadgeDto;
import com.plonit.plonitservice.api.badge.service.dto.GrantMemberRankDto;
import com.plonit.plonitservice.common.CustomApiResponse;
import com.plonit.plonitservice.common.util.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
        Long memberKey = RequestUtils.getMemberKey(servletRequest);

        badgeService.grantBadgeByIndividual(GrantMemberBadgeDto.of(grantMemberBadgeReq, memberKey));

        return CustomApiResponse.ok(null);
    }

    @Operation(summary = "[관리자용] 개인 랭킹 배지 부여", description = "시즌 종료 후 랭킹 배지 부여")
    @PostMapping("/member-rank")
    public CustomApiResponse<Void> grantMemberRank(
            @RequestBody GrantMemberRankReq grantMemberRankReq,
            HttpServletRequest servletRequest
            ) {
        Long memberKey = RequestUtils.getMemberKey(servletRequest);

        badgeService.grantRankBadge(GrantMemberRankDto.of(grantMemberRankReq, memberKey));

        return CustomApiResponse.ok(null);
    }

    @Operation(summary = "[관리자용] 크루 랭킹 배지 부여", description = "시즌 종료 후 랭킹 배지 부여")
    @PostMapping("/crew-rank")
    public CustomApiResponse<Void> grantCrewRank(
            @RequestBody GrantCrewRankReq grantCrewRankReq,
            HttpServletRequest servletRequest
    ) {
        Long memberKey = RequestUtils.getMemberKey(servletRequest);

        badgeService.grantCrewRankBadge(GrantCrewRankDto.of(grantCrewRankReq));

        return CustomApiResponse.ok(null);
    }

    @Operation(summary = "배지 조회", description = "배지를 조회한다.")
    @GetMapping
    public CustomApiResponse<FindBadgeRes> findBadge(
            HttpServletRequest servletRequest
    ) {
        Long memberKey = RequestUtils.getMemberKey(servletRequest);
        return null;
    }

}
