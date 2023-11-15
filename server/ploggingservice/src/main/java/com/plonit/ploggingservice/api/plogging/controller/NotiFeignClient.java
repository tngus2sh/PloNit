package com.plonit.ploggingservice.api.plogging.controller;

import com.plonit.ploggingservice.api.plogging.controller.request.SendNotiReq;
import com.plonit.ploggingservice.common.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "noti-service", url = "https://k9c207.p.ssafy.io/api")
public interface NotiFeignClient {

    @PostMapping("/plonit-service/v1/alarm/help")
    public CustomApiResponse<Long> sendHelpNoti(@RequestHeader("accessToekn") String token, @RequestBody SendNotiReq sendNotiReq);

}
