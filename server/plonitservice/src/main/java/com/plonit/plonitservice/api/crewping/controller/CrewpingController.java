package com.plonit.plonitservice.api.crewping.controller;

import com.plonit.plonitservice.api.crewping.controller.request.SaveCrewpingRecordReq;
import com.plonit.plonitservice.api.crewping.controller.request.SaveCrewpingReq;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingMembersByMasterRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingMembersRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingsRes;
import com.plonit.plonitservice.api.crewping.service.CrewpingQueryService;
import com.plonit.plonitservice.api.crewping.service.CrewpingService;
import com.plonit.plonitservice.api.crewping.service.dto.SaveCrewpingDto;
import com.plonit.plonitservice.api.crewping.service.dto.SaveCrewpingRecordDto;
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
    private final CrewpingQueryService crewpingQueryService;

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

        return CustomApiResponse.ok(null, "크루핑을 생성했습니다.");
    }

    // 크루핑 목록 조회
    @GetMapping("/{crew-id}")
    public CustomApiResponse<List<FindCrewpingsRes>> findCrewpings(@PathVariable("crew-id") Long crewId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("FindCrewpings={}", crewId);

        List<FindCrewpingsRes> response = crewpingQueryService.findCrewpings(RequestUtils.getMemberKey(request), crewId);

        return CustomApiResponse.ok(response, "크루핑 목록 조회를 성공했습니다.");
    }

    // 크루핑 상세 조회
    @GetMapping("/detail/{crewping-id}")
    public CustomApiResponse<FindCrewpingRes> findCrewping(@PathVariable("crewping-id") Long crewpingId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("FindCrewping={}", crewpingId);

        FindCrewpingRes response = crewpingQueryService.findCrewping(RequestUtils.getMemberKey(request), crewpingId);

        return CustomApiResponse.ok(response, "크루핑 상세 조회를 성공했습니다.");
    }

    // 크루핑 참가
    @PostMapping("/join/{crewping-id}")
    public CustomApiResponse<Object> joinCrewping(@PathVariable("crewping-id") Long crewpingId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("JoinCrewping={}", crewpingId);

        crewpingService.joinCrewping(RequestUtils.getMemberKey(request), crewpingId);

        return CustomApiResponse.ok(null, "크루핑에 참가했습니다.");
    }

    // 크루핑 참가 취소
    @DeleteMapping("/quit/{crewping-id}")
    public CustomApiResponse<Object> quitCrewping(@PathVariable("crewping-id") Long crewpingId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("QuitCrewping={}", crewpingId);

        crewpingService.quitCrewping(RequestUtils.getMemberKey(request), crewpingId);

        return CustomApiResponse.ok(null, "크루핑 참가를 취소했습니다.");
    }

    // 크루핑 인원 조회 (크루핑장)
    @GetMapping("/master-member/{crewping-id}")
    public CustomApiResponse<List<FindCrewpingMembersByMasterRes>> findCrewpingMembersByMaster(@PathVariable("crewping-id") Long crewpingId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("FindCrewpingMembersByMaster={}", crewpingId);

        List<FindCrewpingMembersByMasterRes> response = crewpingQueryService.findCrewpingMembersByMaster(RequestUtils.getMemberKey(request), crewpingId);

        return CustomApiResponse.ok(response, "크루핑 현재 인원 조회에 성공했습니다.");
    }

    // 크루핑 인원 조회 (크루핑 멤버)
    @GetMapping("/member/{crewping-id}")
    public CustomApiResponse<List<FindCrewpingMembersRes>> findCrewpingMembers(@PathVariable("crewping-id") Long crewpingId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("FindCrewpingMembers={}", crewpingId);

        List<FindCrewpingMembersRes> response = crewpingQueryService.findCrewpingMembers(RequestUtils.getMemberKey(request), crewpingId);

        return CustomApiResponse.ok(response, "크루핑 현재 인원 조회에 성공했습니다.");
    }

    // 크루핑 강퇴
    @DeleteMapping("/kick-out/{crewping-id}/{target-id}")
    public CustomApiResponse<Object> kickoutCrewpingMember(@PathVariable("crewping-id") Long crewpingId, @PathVariable("target-id") Long targetId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("KickoutCrewpingMember={}, {}", crewpingId, targetId);

        crewpingService.kickoutCrewpingMember(RequestUtils.getMemberKey(request), crewpingId, targetId);

        return CustomApiResponse.ok(null, "크루핑 멤버를 강퇴했습니다.");
    }

    // 크루핑 기록 저장
    @PostMapping("/record")
    public CustomApiResponse<Object> saveCrewpingRecord(@RequestBody SaveCrewpingRecordReq saveCrewpingRecordReq, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("SaveCrewpingRecord={}", saveCrewpingRecordReq);

        SaveCrewpingRecordDto dto = SaveCrewpingRecordDto.of(RequestUtils.getMemberKey(request), saveCrewpingRecordReq);
        crewpingService.saveCrewpingRecord(dto);

        return CustomApiResponse.ok(null, "크루핑 기록을 저장했습니다.");
    }

}
