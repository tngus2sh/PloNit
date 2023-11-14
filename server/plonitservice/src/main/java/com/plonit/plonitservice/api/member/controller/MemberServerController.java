package com.plonit.plonitservice.api.member.controller;

import com.plonit.plonitservice.api.member.controller.response.VolunteerMemberInfoRes;
import com.plonit.plonitservice.api.member.service.MemberQueryService;
import com.plonit.plonitservice.common.CustomApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.START;

@Tag(name = "Member Server API Controller", description = "Member Server API Document")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/plonit-service/na/members")
public class MemberServerController {

    private final MemberQueryService memberQueryService;

    @PostMapping("/volunteer-info")
    public CustomApiResponse<List<VolunteerMemberInfoRes>> findVolunteerInfo(@RequestBody List<Long> memberIdList) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("FindVolunteerInfo={}", memberIdList);

        List<VolunteerMemberInfoRes> response = memberQueryService.findVolunteerInfo(memberIdList);

        return CustomApiResponse.ok(response);
    }
}
