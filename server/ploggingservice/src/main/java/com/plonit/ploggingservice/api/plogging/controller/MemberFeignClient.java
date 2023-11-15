package com.plonit.ploggingservice.api.plogging.controller;

import com.plonit.ploggingservice.api.plogging.controller.request.UpdateVolunteerInfoReq;
import com.plonit.ploggingservice.common.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "member-service", url = "https://k9c207.p.ssafy.io/api")
public interface MemberFeignClient {

//    @PatchMapping("/plonit-service/api/members/volunteer")
    @PutMapping("/plonit-service/v1/members/volunteer")
    CustomApiResponse<Long> updateVolunteerInfo(
            @RequestHeader("accessToken") String token,
            @RequestBody UpdateVolunteerInfoReq req
            );
}
