package com.plonit.plonitservice.api.crew.controller;

import com.plonit.plonitservice.api.crew.controller.request.SaveCrewReq;
import com.plonit.plonitservice.api.crew.service.CrewService;
import com.plonit.plonitservice.api.crew.service.DTO.SaveCrewDTO;
import com.plonit.plonitservice.common.CustomApiResponse;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.util.RequestUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.END;

@Tag(name = "Crew", description = "크루")
@Slf4j
@RequestMapping("/api/plonit-service/v1/crew")
@RestController
@RequiredArgsConstructor
public class CrewController {
    private final CrewService crewService;
    @PostMapping // 크루 생성
    public CustomApiResponse<Object> saveCrew (@ModelAttribute SaveCrewReq saveCrewReq, HttpServletRequest request, Errors errors) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("error message : " + e.getDefaultMessage());
            });
            log.info(logCurrent(getClassName(), getMethodName(), END));
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        Long memberKey = RequestUtils.getMemberKey(request);
        crewService.saveCrew(SaveCrewDTO.of(memberKey, saveCrewReq));
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok("", "크루 생성에 성공했습니다.");
    }
}
