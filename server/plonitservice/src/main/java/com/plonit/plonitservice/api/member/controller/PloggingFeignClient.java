package com.plonit.plonitservice.api.member.controller;

import com.plonit.plonitservice.common.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "plogging-service", url = "https://k9c207.p.ssafy.io/api")
public interface PloggingFeignClient {

    @GetMapping("/plogging-service/v1/count")
    CustomApiResponse<Integer> countPlogging();
    
}
