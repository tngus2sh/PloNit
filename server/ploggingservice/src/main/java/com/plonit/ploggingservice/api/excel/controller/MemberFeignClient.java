package com.plonit.ploggingservice.api.excel.controller;

import com.plonit.ploggingservice.api.excel.controller.response.VolunteerInfoRes;
import com.plonit.ploggingservice.common.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "member-service", contextId = "feignClientForExcel", url = "https://k9c207.p.ssafy.io/api")
public interface MemberFeignClient {

    @GetMapping("/plonit-service/")
    CustomApiResponse<List<VolunteerInfoRes>> findVolunteerMemberInfo();
}
