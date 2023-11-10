package com.plonit.plonitservice.api.region.controller;

import com.plonit.plonitservice.api.region.controller.response.DongRes;
import com.plonit.plonitservice.api.region.controller.response.GugunRes;
import com.plonit.plonitservice.api.region.controller.response.SidoRes;
import com.plonit.plonitservice.api.region.service.RegionQueryService;
import com.plonit.plonitservice.common.CustomApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Region API Controller", description = "Region API Document")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/plonit-service/na/region")
public class RegionApiController {

    private final RegionQueryService regionQueryService;

    /**
     * 시도 정보 반환
     * @return 시도 리스트 (시도 코드, 이름)
     */
    @GetMapping("/sido")
    public CustomApiResponse<List<SidoRes>> findSido() {
        List<SidoRes> sido = regionQueryService.findSido();
        return CustomApiResponse.ok(sido);
    }

    /**
     * 구군 정보 반환
     * @param sidoCode 시도 코드
     * @return 구군 리스트 (구군 코드, 이름)
     */
    @GetMapping("/gugun/{sido-code}")
    public CustomApiResponse<List<GugunRes>> findGugun(
            @PathVariable("sido-code") Long sidoCode
    ) {
        List<GugunRes> gugun = regionQueryService.findGugun(sidoCode);
        return CustomApiResponse.ok(gugun);
    }

    /**
     * 동 정보 반환
     * @param gugunCode 구군 코드
     * @return 동 리스트 (동 코드, 이름)
     */
    @GetMapping("/dong/{gugun-code}")
    public CustomApiResponse<List<DongRes>> findDong(
            @PathVariable("gugun-code") Long gugunCode
    ) {
        List<DongRes> dong = regionQueryService.findDong(gugunCode);
        return CustomApiResponse.ok(dong);
    }
}
