package com.plonit.ploggingservice.api.excel.controller;

import com.plonit.ploggingservice.api.excel.controller.response.VolunteerMemberInfoRes;
import com.plonit.ploggingservice.common.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@FeignClient(name = "member-service", contextId = "feignClientForExcel", url = "https://k9c207.p.ssafy.io/api")
@FeignClient(name = "member-service", contextId = "feignClientForExcel", url = "http://localhost:8000/api")
public interface MemberFeignClient {

    @PostMapping("/plonit-service/na/members/volunteer-info")
    CustomApiResponse<List<VolunteerMemberInfoRes>> findVolunteerMemberInfo(@RequestBody List<Long> memberIdList);

}
