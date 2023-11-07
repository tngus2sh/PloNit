package com.plonit.ploggingservice.api.plogging.controller;

import com.plonit.ploggingservice.api.plogging.controller.request.CrewpingRecordReq;
import com.plonit.ploggingservice.common.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "crewping-service", url = "https://k9c207.p.ssafy.io/api")
public interface CrewpingFeignClient {

    @PostMapping("/plonit-service/v1/crewping/record")
    CustomApiResponse<Long> saveCrewpingRecord(@RequestBody CrewpingRecordReq crewpingRecordReq);
    
}
