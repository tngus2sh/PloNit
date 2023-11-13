package com.plonit.ploggingservice.api.plogging.controller;

import com.plonit.ploggingservice.api.plogging.controller.request.*;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingHelpRes;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingLogRes;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingPeriodRes;
import com.plonit.ploggingservice.api.plogging.controller.response.UsersRes;
import com.plonit.ploggingservice.api.plogging.service.PloggingQueryService;
import com.plonit.ploggingservice.api.plogging.service.PloggingService;
import com.plonit.ploggingservice.api.plogging.service.dto.*;
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
import javax.validation.Valid;
import java.util.List;

import static com.plonit.ploggingservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;
import static com.plonit.ploggingservice.common.util.LogCurrent.*;


@Tag(name = "Plogging API Controller", description = "플로깅 API Document")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/plogging-service/v1")
@Validated
public class PloggingApiController {
    
    private final PloggingService ploggingService;
    private final PloggingQueryService ploggingQueryService;
    
    @Operation(summary = "플로깅 시작하기", description = "플로깅을 시작할 때 초기 플로깅 정보들을 저장합니다.")
    @PostMapping("/start")
    public CustomApiResponse<Long> saveStartPlogging(
            @Valid @RequestBody StartPloggingReq request,
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

        // 플로깅 시작하기
        Long ploggingId = ploggingService.saveStartPlogging(StartPloggingDto.of(request, memberKey));
        
        return CustomApiResponse.ok(ploggingId);
    }
    
    @Operation(summary = "플로깅 종료시 기록 저장", description = "플로깅 종료시에 해당하는 플로깅 id에 추가 정보들을 넣는다.")
    @PostMapping
    public CustomApiResponse<Long> saveEndPlogging(
            @Valid @RequestBody EndPloggingReq request,
            HttpServletRequest servletRequest,
            Errors errors
            ) {
        
        log.info("endStartPlogging : {}", request);

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("message : " + e.getDefaultMessage());
            });
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }
        
        Long memberKey = RequestUtils.getMemberKey(servletRequest);
        log.info("memberKey : " + memberKey);

        // 플로깅 기록 저장
        Long ploggingId = ploggingService.saveEndPlogging(EndPloggingDto.of(request, memberKey));

        return CustomApiResponse.ok(ploggingId);
    }
    
    @Operation(summary = "플로깅 기록 일별 조회", description = "처음 날짜와 마지막 날짜를 지정해 해당 기간에 포함되어 있는 플로깅들을 조회한다.")
    @GetMapping("/period/{start-day}/{end-day}")
    public CustomApiResponse<List<PloggingPeriodRes>> findPloggingLogbyDay(
            @PathVariable(value = "start-day") String startDay,
            @PathVariable(value = "end-day") String endDay,
            @RequestParam(name = "type", required = false, defaultValue = "ALL") String type,
            HttpServletRequest servletRequest
    ) {
        // 플로깅 기록 일별 조회 
        Long memberKey = RequestUtils.getMemberKey(servletRequest);
        log.info("memberKey : " + memberKey);

        List<PloggingPeriodRes> ploggingLogByDay = ploggingQueryService.findPloggingLogByDay(startDay, endDay, memberKey, type);

        return CustomApiResponse.ok(ploggingLogByDay);
    }
    
    @Operation(summary = "플로깅 기록 상세 조회", description = "플로깅 id에 해당하는 플로깅에 대한 상세 정보를 불러온다.")
    @GetMapping("/{plogging-id}")
    public CustomApiResponse<PloggingLogRes> findPloggingLogDetail(
            @PathVariable(value = "plogging-id") Long ploggingId,
            HttpServletRequest servletRequest
    ) {
        // 플로깅 기록 상세 조회 
        Long memberKey = RequestUtils.getMemberKey(servletRequest);
        log.info("memberKey : " + memberKey);

        PloggingLogRes ploggingLogDetail = ploggingQueryService.findPloggingLogDetail(ploggingId, memberKey);

        return CustomApiResponse.ok(ploggingLogDetail);
    }
    
    @Operation(summary = "플로깅 도움 요청 저장", description = "플로깅 도움 요청을 보낼 때 해당 정보들을 저장한다.")
    @PostMapping("/help")
    public CustomApiResponse<Long> savePloggingHelp(
            @Valid @ModelAttribute HelpPloggingReq request,
            HttpServletRequest servletRequest,
            Errors errors
            ) {

        log.info("savePloggingHelp : {}", request);

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("message : " + e.getDefaultMessage());
            });
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }
        
        Long memberKey = RequestUtils.getMemberKey(servletRequest);
        log.info("memberKey : " + memberKey);

        // 플로깅 도움 요청 저장
        Long ploggingHelpId = ploggingService.savePloggingHelp(HelpPloggingDto.of(request, memberKey));

        return CustomApiResponse.ok(ploggingHelpId);
        
    }
    
    @Operation(summary = "플로깅 도움 요청 지역별 조회", description = "위도와 경도를 보내서 해당 위치 구에 있는 도움 요청들을 보낸다.")
    @GetMapping("/help/{latitude}/{longitude}")
    public CustomApiResponse<List<PloggingHelpRes>> findPloggingHelp (
            @PathVariable(value = "latitude") Double latitude,
            @PathVariable(value = "longitude") Double longitude
    ) {

        // 플로깅 도움 요청 지역별 조회 

        List<PloggingHelpRes> ploggingHelp = ploggingQueryService.findPloggingHelp(latitude, longitude);

        return CustomApiResponse.ok(ploggingHelp);
    }
    
    @Operation(summary = "플로깅 중간에 이미지 전송", description = "플로깅 중간에 이미지를 전송하고자 할때 이미지 정보를 넣는다.")
    @PostMapping("/image")
    public CustomApiResponse<String> savePloggingImage(
            @Valid @ModelAttribute ImagePloggingReq request,
            Errors errors
            ) {

        log.info("savePloggingImage : {}", request);

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("message : " + e.getDefaultMessage());
            });
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        // 플로깅 중간에 이미지 저장 
        String imageUrl = ploggingService.savePloggingImage(ImagePloggingDto.of(request));

        return CustomApiResponse.ok(imageUrl);
        
    }
    
    @Operation(summary = "플로깅 주변의 유저 조회", description = "위도, 경도에 맞는 '구'를 가져와 해당 구에 있는 유저를 조회한다.")
    @GetMapping("/users/{latitude}/{longitude}")
    public CustomApiResponse<UsersRes> findPloggingUsers(
            @PathVariable(value = "latitude") Double latitude,
            @PathVariable(value = "longitude") Double longitude
    ) {
        // TODO: 2023-10-27 주변에 있는 유저 조회 
        
        
        return null;
    }

    @Operation(summary = "유저별 플로깅 참여 횟수 조회", description = "유저 아이디를 가지고 플로깅에 참여한 횟수를 조회한다.")
    @GetMapping("/count")
    public CustomApiResponse<Integer> countMemberPlogging() {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("CountMemberPlogging");

        Integer response = ploggingQueryService.countMemberPlogging();

        return CustomApiResponse.ok(response, "크루핑 참여 횟수 조회에 성공했습니다.");
    }

    @Operation(summary = "봉사 블로깅 정보 저장", description = "봉사 플로깅 종료 후 봉사 데이터 정보 저장")
    @PostMapping("/volunteer")
    public CustomApiResponse<Long> saveVolunteerData(
            @RequestBody VolunteerPloggingReq volunteerPloggingReq,
            HttpServletRequest servletRequest
            ) {
        log.info("saveVolunteerData = {}", volunteerPloggingReq.toString());

        Long memberKey = RequestUtils.getMemberKey(servletRequest);

        Long volunteerId = ploggingService.saveVolunteerData(VolunteerPloggingDto.of(volunteerPloggingReq, memberKey));

        return CustomApiResponse.ok(volunteerId);
    }

}
