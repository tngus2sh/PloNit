package com.plonit.plonitservice.api.feed.controller;

import com.plonit.plonitservice.api.feed.controller.request.SaveFeedReq;
import com.plonit.plonitservice.api.feed.controller.response.FindFeedRes;
import com.plonit.plonitservice.common.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.END;

@Tag(name = "Feed API Controller", description = "Feed API Document")
@Slf4j
@RequestMapping("/api/plonit-service/v1/feed")
@RestController
@RequiredArgsConstructor
public class FeedController {

    @Operation(summary = "피드 생성", description = "사용자는 크루를 생성한다.")
    @PostMapping // 피드 생성
    public CustomApiResponse<Object> saveFeed(@Validated @ModelAttribute SaveFeedReq saveFeedReq, HttpServletRequest request, Errors errors) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info(saveFeedReq.toString());
        // todo : 피드 생성 service
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok("", "피드 생성에 성공했습니다.");
    }

    @Operation(summary = "피드 조회", description = "피드를 조회한다.")
    @GetMapping // 피드 조회
    public CustomApiResponse<Object> findCrews(HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        FindFeedRes findFeedRes = FindFeedRes.builder().build();
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(findFeedRes, "피드 조회에 성공했습니다.");
    }
}
