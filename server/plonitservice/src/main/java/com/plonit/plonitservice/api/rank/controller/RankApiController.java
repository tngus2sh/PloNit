package com.plonit.plonitservice.api.rank.controller;

import com.plonit.plonitservice.api.rank.controller.request.CrewRankRequest;
import com.plonit.plonitservice.api.rank.controller.request.IndividualRankRequest;
import com.plonit.plonitservice.api.rank.controller.response.CrewAvgResponse;
import com.plonit.plonitservice.api.rank.controller.response.CrewTotalResponse;
import com.plonit.plonitservice.api.rank.controller.response.MembersRankResponse;
import com.plonit.plonitservice.api.rank.service.RankService;
import com.plonit.plonitservice.common.CustomApiResponse;
import com.plonit.plonitservice.common.util.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Rank API Controller", description = "Rank API Document")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/plonit-service/v1/rank")
public class RankApiController {
    
    private final RankService rankService;

    @Operation(summary = "회원 랭킹 조회", description = "전체 회원들의 랭킹을 조회합니다.")
    @GetMapping
    public CustomApiResponse<MembersRankResponse> findAllMembersRank(
            HttpServletRequest request
    ) {
        // 회원 랭킹 조회
        Long memberkey = RequestUtils.getMemberKey(request);
        MembersRankResponse allMembersRank = rankService.findAllMembersRank(memberkey);
        return CustomApiResponse.ok(allMembersRank);
    }

    @Operation(summary = "크루 전체 랭킹 조회", description = "크루 전체의 랭킹을 조회합니다.")
    @GetMapping("/crew-total")

    public CustomApiResponse<CrewTotalResponse> findAllCrewRank(
            HttpServletRequest request
    ) {
        // 크루 전체 랭킹 조회
        Long memberKey = RequestUtils.getMemberKey(request);
        CrewTotalResponse allCrewRank = rankService.findAllCrewRank(memberKey);
        return CustomApiResponse.ok(allCrewRank);
    }

    @Operation(summary = "크루 평균 랭킹 조회", description = "크루 평균 랭킹을 조회합니다.")
    @GetMapping("/crew-avg")
    public CustomApiResponse<List<CrewAvgResponse>> findAllCrewRankByAVG() {

        // TODO: 2023-10-30 크루 평균 랭킹 조회 

        return null;
    }


    @Operation(summary = "[관리자용] 개인 랭킹 설정", description = "개인 랭킹을 설정합니다.")
    @PostMapping("/member-reset")
    public CustomApiResponse<Void> saveIndividualRank(
            @Validated @RequestBody IndividualRankRequest request
    ) {

        // TODO: 2023-10-30 개인 랭킹 설정 

        return null;
    }

    @Operation(summary = "[관리자용] 크루 랭킹 설정", description = "크루 랭킹을 설정합니다.")
    @PostMapping("/crew-reset")
    public CustomApiResponse<Void> saveCrewRank(
            @Validated @RequestBody CrewRankRequest request
    ) {

        // TODO: 2023-10-30 크루 랭킹 설정 

        return null;
    }


}
