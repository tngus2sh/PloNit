package com.plonit.ploggingservice.api.plogging.controller;


import com.plonit.ploggingservice.api.plogging.controller.request.GrantMemberBadgeReq;
import com.plonit.ploggingservice.common.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "badge-service", url = "https://k9c207.p.ssafy.io/api")
public interface BadgeFeignClient {

    CustomApiResponse<Void> grantMemberBadge(@RequestBody GrantMemberBadgeReq grantMemberBadgeReq);

}
