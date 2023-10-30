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
import com.plonit.ploggingservice.api.plogging.service.dto.StartPloggingDto;
import com.plonit.ploggingservice.common.CustomApiResponse;
import com.plonit.ploggingservice.common.exception.CustomException;
import com.plonit.ploggingservice.common.util.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.plonit.ploggingservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;


@Tag(name = "Plogging API Controller", description = "플로깅 API Document")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/plogging-service/v1")
public class PloggingApiController {
    
    private final PloggingService ploggingService;
    
    @Operation(summary = "플로깅 시작하기", description = "플로깅을 시작할 때 초기 플로깅 정보들을 저장합니다.")
    @PostMapping("/start")
    public CustomApiResponse<Long> saveStartPlogging(
            @Validated @RequestBody StartPloggingRequest request,
            HttpServletRequest servletRequest,
            Errors errors
            ) {
        
        log.info("saveStartPlogging : {}", request);

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("message : " + e.getDefaultMessage());
            });
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        Long memberKey = RequestUtils.getMemberKey(servletRequest);
        log.info("memberKey : " + memberKey);

        // TODO: 2023-10-27 플로깅 시작하기 
        ploggingService.saveStartPlogging(StartPloggingRequest.toDto(request));
        
        return CustomApiResponse.ok(1l);
    }
    
    @Operation(summary = "플로깅 종료시 기록 저장", description = "플로깅 종료시에 해당하는 플로깅 id에 추가 정보들을 넣는다.")
    @PostMapping
    public CustomApiResponse<Long> saveEndPlogging(
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
    
    @Operation(summary = "플로깅 기록 일별 조회", description = "처음 날짜와 마지막 날짜를 지정해 해당 기간에 포함되어 있는 플로깅들을 조회한다.")
    @GetMapping("/period/{start-day}-{end-day}")
    public CustomApiResponse<List<PloggingPeriodResponse>> findPloggingLogbyDay(
            @PathVariable(value = "start-day") String startDay,
            @PathVariable(value = "end-day") String endDay
    ) {
        // TODO: 2023-10-27 플로깅 기록 일별 조회 

        return null;
    }
    
    @Operation(summary = "플로깅 기록 상세 조회", description = "플로깅 id에 해당하는 플로깅에 대한 상세 정보를 불러온다.")
    @GetMapping("/{plogging-id}")
    public CustomApiResponse<PloggingLogResponse> findPloggingLogDetail(
            @PathVariable(value = "plogging-id") Long ploggingId
    ) {
        // TODO: 2023-10-27 플로깅 기록 상세 조회 
        return null;
    }
    
    @Operation(summary = "플로깅 도움 요청 저장", description = "플로깅 도움 요청을 보낼 때 해당 정보들을 저장한다.")
    @PostMapping("/help")
    public CustomApiResponse<Long> savePloggingHelp(
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
    
    @Operation(summary = "플로깅 도움 요청 지역별 조회", description = "위도와 경도를 보내서 해당 위치 구에 있는 도움 요청들을 보낸다.")
    @GetMapping("/help/{latitude}-{longitude}")
    public CustomApiResponse<PloggingHelpResponse> findPloggingHelp (
            @PathVariable(value = "latitude") Double latitude,
            @PathVariable(value = "longitude") Double longitude
    ) {

        // TODO: 2023-10-27 플로깅 도움 요청 지역별 조회 

        return null;
    }
    
    @Operation(summary = "플로깅 중간에 이미지 전송", description = "플로깅 중간에 이미지를 전송하고자 할때 이미지 정보를 넣는다.")
    @PostMapping("/image")
    public CustomApiResponse<Long> savePloggingImage(
            @Validated @ModelAttribute ImagePloggingRequest request,
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
    
    @Operation(summary = "플로깅 주변의 유저 조회", description = "위도, 경도에 맞는 '구'를 가져와 해당 구에 있는 유저를 조회한다.")
    @GetMapping("/users/{latitude}-{longitude}")
    public CustomApiResponse<UsersResponse> findPloggingUsers(
            @PathVariable(value = "latitude") Double latitude,
            @PathVariable(value = "longitude") Double longitude
    ) {
        // TODO: 2023-10-27 주변에 있는 유저 조회 
        
        return null;
    }
}
