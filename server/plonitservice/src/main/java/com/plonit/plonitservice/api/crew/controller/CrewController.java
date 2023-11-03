package com.plonit.plonitservice.api.crew.controller;

import com.plonit.plonitservice.api.crew.controller.request.SaveCrewReq;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewRes;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewsRes;
import com.plonit.plonitservice.api.crew.service.CrewQueryService;
import com.plonit.plonitservice.api.crew.service.CrewService;
import com.plonit.plonitservice.api.crew.service.dto.SaveCrewDto;
import com.plonit.plonitservice.common.CustomApiResponse;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.util.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.END;

@Tag(name = "Crew API Controller", description = "Crew API Document")
@Slf4j
@RequestMapping("/api/plonit-service/v1/crew")
@RestController
@RequiredArgsConstructor
public class CrewController {
    private final CrewService crewService;
    private final CrewQueryService crewQueryService;

    @Operation(summary = "크루 생성하기", description = "사용자는 크루를 생성한다.")
    @PostMapping // 크루 생성
    public CustomApiResponse<Object> saveCrew(@Validated @ModelAttribute SaveCrewReq saveCrewReq, HttpServletRequest request, Errors errors) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info(saveCrewReq.toString());

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("error message : " + e.getDefaultMessage());
            });
            log.info(logCurrent(getClassName(), getMethodName(), END));
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        Long memberKey = RequestUtils.getMemberKey(request);
        crewService.saveCrew(SaveCrewDto.of(memberKey, saveCrewReq));

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok("", "크루 생성에 성공했습니다.");
    }

    @Operation(summary = "크루 목록 조회", description = "크루의 목록을 조회한다.")
    @GetMapping // 크루 목록 조회
    public CustomApiResponse<Object> findCrews(HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        List<FindCrewsRes> findCrewResList = crewQueryService.findCrews();


        if (findCrewResList.isEmpty()) {
            log.info(logCurrent(getClassName(), getMethodName(), END));
            return CustomApiResponse.of(0, HttpStatus.NO_CONTENT, "크루 목록 조회에 성공했습니다.");
        }

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(findCrewResList, "크루 목록 조회에 성공했습니다.");
    }

    @Operation(summary = "크루 상세 조회", description = "크루를 상세 조회한다.")
    @GetMapping ("/{crew-id}") // 크루 상세 조회
    public CustomApiResponse<Object> findCrew(@PathVariable("crew-id") long crewId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        FindCrewRes findCrewRes = crewQueryService.findCrew(crewId);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(findCrewRes, "크루 상세 조회에 성공했습니다.");
    }
}
