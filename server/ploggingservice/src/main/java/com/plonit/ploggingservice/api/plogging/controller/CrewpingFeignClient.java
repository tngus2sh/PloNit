package com.plonit.ploggingservice.api.plogging.controller;

import com.plonit.ploggingservice.api.plogging.controller.request.CheckCrewpingMaster;
import com.plonit.ploggingservice.api.plogging.controller.request.CrewpingRecordReq;
import com.plonit.ploggingservice.api.plogging.controller.request.UpdateCrewpingStatusReq;
import com.plonit.ploggingservice.common.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "crewping-service", url = "https://k9c207.p.ssafy.io/api")
public interface CrewpingFeignClient {

    @PostMapping("/plonit-service/v1/crewping/master")
    CustomApiResponse<Boolean> isCrewpingMaster(@RequestBody CheckCrewpingMaster checkCrewpingMaster);

    @PostMapping("/plonit-service/v1/crewping/record")
    CustomApiResponse<Long> saveCrewpingRecord(@RequestBody CrewpingRecordReq crewpingRecordReq);

    @PatchMapping("/plonit-service/v1/crewping/status")
    CustomApiResponse<Long> updateCrewpingStatus(@RequestBody UpdateCrewpingStatusReq updateCrewpingStatusReq);
}
