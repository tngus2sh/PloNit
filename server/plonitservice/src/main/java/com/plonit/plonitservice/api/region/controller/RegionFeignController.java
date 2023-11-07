package com.plonit.plonitservice.api.region.controller;

import com.plonit.plonitservice.api.region.controller.response.SidoGugunCodeRes;
import com.plonit.plonitservice.api.region.service.RegionQueryService;
import com.plonit.plonitservice.common.CustomApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Region API Controller", description = "Region API Document")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/plonit-service/na/region")
public class RegionFeignController {

    private final RegionQueryService regionQueryService;
    
    @GetMapping("/code/{sido-name}/{gugun-name}")
    CustomApiResponse<SidoGugunCodeRes> findSidogugunCode(
            @PathVariable("sido-name") String sidoName,
            @PathVariable("gugun-name") String gugunName
    ) {
        log.info("findSidoGugunCodeRes = {}", sidoName, gugunName);
        SidoGugunCodeRes sidogugunCode = regionQueryService.findSidogugunCode(sidoName, gugunName);
        return CustomApiResponse.ok(sidogugunCode);
    }
}
