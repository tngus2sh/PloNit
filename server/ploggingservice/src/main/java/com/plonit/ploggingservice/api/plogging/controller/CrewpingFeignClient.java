package com.plonit.ploggingservice.api.plogging.controller;

import com.plonit.ploggingservice.api.plogging.controller.request.CrewpingRecordReq;
import com.plonit.ploggingservice.api.plogging.controller.request.UpdateCrewpingStatusReq;
import com.plonit.ploggingservice.common.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "plonit-service", url = "https://k9c207.p.ssafy.io/api")
public interface CrewpingFeignClient {

    @GetMapping("/plonit-service/v1/crewping/master/{crewping-id}")
    CustomApiResponse<Boolean> isCrewpingMaster(@RequestHeader("accessToken") String token, @PathVariable("crewping-id") Long crewpingId);

    @PostMapping("/plonit-service/v1/crewping/record")
    CustomApiResponse<Long> saveCrewpingRecord(@RequestHeader("accessToken") String token, @RequestBody CrewpingRecordReq crewpingRecordReq);

    @PatchMapping("/plonit-service/v1/crewping/status")
    CustomApiResponse<Long> updateCrewpingStatus(@RequestHeader("accessToekn") String token, @RequestBody UpdateCrewpingStatusReq updateCrewpingStatusReq);
}
