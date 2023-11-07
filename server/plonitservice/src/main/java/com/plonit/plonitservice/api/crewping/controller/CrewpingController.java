package com.plonit.plonitservice.api.crewping.controller;

import com.plonit.plonitservice.api.crewping.controller.request.SaveCrewpingReq;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingsRes;
import com.plonit.plonitservice.api.crewping.service.CrewpingService;
import com.plonit.plonitservice.api.crewping.service.dto.SaveCrewpingDto;
import com.plonit.plonitservice.common.CustomApiResponse;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.util.RequestUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.START;

@Tag(name = "Crewping", description = "크루핑")
@Slf4j
@RequestMapping("/api/plonit-service/v1/crewping")
@RestController
@RequiredArgsConstructor
public class CrewpingController {

    private final CrewpingService crewpingService;

    // 크루핑 생성
    @PostMapping
    public CustomApiResponse<Object> saveCrewping(@Validated @ModelAttribute SaveCrewpingReq saveCrewpingReq, HttpServletRequest request, Errors errors) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("SaveCrewping={}", saveCrewpingReq);

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("error message : " + e.getDefaultMessage());
            });
            log.info(logCurrent(getClassName(), getMethodName(), END));
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        Long memberKey = RequestUtils.getMemberKey(request);
        crewpingService.saveCrewping(SaveCrewpingDto.of(memberKey, saveCrewpingReq));

        return CustomApiResponse.ok(null);
    }

    // 크루핑 목록 조회
    @GetMapping("/{crew-id}")
    public CustomApiResponse<List<FindCrewpingsRes>> findCrewpings(@PathVariable("crew-id") Long crewId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("FindCrewpings={}", crewId);

        List<FindCrewpingsRes> response = crewpingService.findCrewpings(RequestUtils.getMemberKey(request), crewId);

        return CustomApiResponse.ok(response);
    }

    // 크루핑 상세 조회
    @GetMapping("/detail/{crewping-id}")
    public CustomApiResponse<FindCrewpingRes> findCrewping(@PathVariable("crewping-id") Long crewpingId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("FindCrewping={}", crewpingId);

        FindCrewpingRes response = crewpingService.findCrewping(RequestUtils.getMemberKey(request), crewpingId);

        return CustomApiResponse.ok(response);
    }

    // 크루핑 참가
    @PostMapping("/join/{crewping-id}")
    public CustomApiResponse<Object> joinCrewping(@PathVariable("crewping-id") Long crewpingId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("JoinCrewping={}", crewpingId);

        crewpingService.joinCrewping(RequestUtils.getMemberKey(request), crewpingId);

        return CustomApiResponse.ok("", "크루핑에 참가했습니다.");
    }
}
