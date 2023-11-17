package com.plonit.plonitservice.api.member.controller;

import com.plonit.plonitservice.common.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;

@FeignClient(name = "plogging-service", url = "https://k9c207.p.ssafy.io/api")
//@FeignClient(name = "plogging-service", url = "http://localhost:8000/api")
public interface PloggingFeignClient {

    @GetMapping("/plogging-service/v1/count")
    CustomApiResponse<Integer> countPlogging(@RequestHeader("accessToken") String token);

    @GetMapping("/plogging-service/v1/countCrew")
    CustomApiResponse<HashMap<Long, Long>> countCrewPlogging(@RequestHeader("accessToken") String token);
    
}
