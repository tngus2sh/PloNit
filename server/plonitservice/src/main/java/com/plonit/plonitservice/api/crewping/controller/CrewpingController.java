package com.plonit.plonitservice.api.crewping.controller;

import com.plonit.plonitservice.api.crewping.controller.request.SaveCrewpingReq;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    }

}
