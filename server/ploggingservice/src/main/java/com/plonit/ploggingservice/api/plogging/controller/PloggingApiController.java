package com.plonit.ploggingservice.api.plogging.controller;

import com.plonit.ploggingservice.api.plogging.controller.request.EndPloggingRequest;
import com.plonit.ploggingservice.api.plogging.controller.request.HelpPloggingRequest;
import com.plonit.ploggingservice.api.plogging.controller.request.ImagePloggingRequest;
import com.plonit.ploggingservice.api.plogging.controller.request.StartPloggingRequest;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingHelpResponse;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingLogResponse;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingPeriodResponse;
import com.plonit.ploggingservice.api.plogging.controller.response.UsersResponse;
import com.plonit.ploggingservice.api.plogging.service.PloggingService;
import com.plonit.ploggingservice.common.CustomApiResponse;
import com.plonit.ploggingservice.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.plonit.ploggingservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/plogging-service/v1/{member-key}")
public class PloggingApiController {
    
    private final PloggingService ploggingService;
    
    
    @PostMapping("/start")
    public CustomApiResponse<Long> saveStartPlogging(
            @PathVariable(value = "member-key") String memberKey,
            @Validated @RequestBody StartPloggingRequest request,
            Errors errors
            ) {
        
        log.info("saveStartPlogging : {}", request);

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("message : " + e.getDefaultMessage());
            });
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        // TODO: 2023-10-27 플로깅 시작하기 
        
        return CustomApiResponse.ok(1l);
    }
    
    
    @PostMapping
    public CustomApiResponse<Long> saveEndPlogging(
            @PathVariable(value = "member-key") String memberKey,
            @Validated @RequestBody EndPloggingRequest request,
            Errors errors
            ) {
        
        log.info("endStartPlogging : {}", request);

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("message : " + e.getDefaultMessage());
            });
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        // TODO: 2023-10-27 플로깅 기록 저장 

        return CustomApiResponse.ok(1l);
    }
    
    @GetMapping("/period/{start-day}-{end-day}")
    public CustomApiResponse<List<PloggingPeriodResponse>> findPloggingLogbyDay(
            @PathVariable(value = "member-key") String memberKey
    ) {
        // TODO: 2023-10-27 플로깅 기록 일별 조회 

        return null;
    }
    
    @GetMapping("/{plogging-id}")
    public CustomApiResponse<PloggingLogResponse> findPloggingLogDetail(
            @PathVariable(value = "member-key") String memberKey,
            @PathVariable(value = "plogging-id") Long ploggingId
    ) {
        // TODO: 2023-10-27 플로깅 기록 상세 조회 
        return null;
    }
    
    @PostMapping("/help")
    public CustomApiResponse<Long> savePloggingHelp(
            @PathVariable(value = "member-key") String memberKey,
            @Validated @RequestBody HelpPloggingRequest request,
            Errors errors
            ) {

        log.info("savePloggingHelp : {}", request);

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("message : " + e.getDefaultMessage());
            });
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        // TODO: 2023-10-27 플로깅 도움 요청 저장

        return CustomApiResponse.ok(1l);
        
    }
    
    @GetMapping("/help/{latitude}-{longitude}")
    public CustomApiResponse<PloggingHelpResponse> findPloggingHelp (
            @PathVariable(value = "member-key") String memberKey,
            @PathVariable(value = "latitude") Double latitude,
            @PathVariable(value = "longitude") Double longitude
    ) {

        // TODO: 2023-10-27 플로깅 도움 요청 지역별 조회 

        return null;
    }
    
    @PostMapping("/image")
    public CustomApiResponse<Long> savePloggingImage(
            @PathVariable(value = "member-key") String memberKey,
            @Validated @RequestBody ImagePloggingRequest request,
            Errors errors
            ) {

        log.info("savePloggingImage : {}", request);

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("message : " + e.getDefaultMessage());
            });
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        // TODO: 2023-10-27 플로깅 중간에 이미지 저장 
        
        return null;
        
    }
    
    
    @GetMapping("/users/{latitude}-{longitude}")
    public CustomApiResponse<UsersResponse> findPloggingUsers(
            @PathVariable(value = "member-key") String memberKey,
            @PathVariable(value = "latitude") Double latitude,
            @PathVariable(value = "longitude") Double longitude
    ) {
        // TODO: 2023-10-27 주변에 있는 유저 조회 
        
        return null;
    }
}
