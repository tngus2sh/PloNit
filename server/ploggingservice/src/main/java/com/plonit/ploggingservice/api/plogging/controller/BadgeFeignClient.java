package com.plonit.ploggingservice.api.plogging.controller;


import com.plonit.ploggingservice.api.plogging.controller.request.GrantMemberBadgeReq;
import com.plonit.ploggingservice.common.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "badge-service", url = "https://k9c207.p.ssafy.io/api")
//@FeignClient(name = "badge-service", url = "localhost:8000/api")
public interface BadgeFeignClient {

    @PostMapping("/plonit-service/v1/badge/member")
    CustomApiResponse<Void> grantMemberBadge(@RequestHeader("accessToken") String token, @RequestBody GrantMemberBadgeReq grantMemberBadgeReq);

}
