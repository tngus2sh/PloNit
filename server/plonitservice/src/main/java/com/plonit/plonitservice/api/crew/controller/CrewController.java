package com.plonit.plonitservice.api.crew.controller;

import com.plonit.plonitservice.api.crew.controller.request.ApproveCrewReq;
import com.plonit.plonitservice.api.crew.controller.request.SaveCrewNoticeReq;
import com.plonit.plonitservice.api.crew.controller.request.SaveCrewReq;
import com.plonit.plonitservice.api.crew.controller.request.UpdateCrewNoticeReq;
import com.plonit.plonitservice.api.crew.controller.response.*;
import com.plonit.plonitservice.api.crew.service.CrewQueryService;
import com.plonit.plonitservice.api.crew.service.CrewService;
import com.plonit.plonitservice.api.crew.service.dto.ApproveCrewDto;
import com.plonit.plonitservice.api.crew.service.dto.SaveCrewDto;
import com.plonit.plonitservice.api.crew.service.dto.SaveCrewNoticeDto;
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

    @Operation(summary = "크루원 조회(크루원)", description = "크루원을 조회한다.")
    @GetMapping ("/member/{crew-id}")
    public CustomApiResponse<Object> findCrewMember(@PathVariable("crew-id") long crewId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        List<FindCrewMemberRes> findCrewMemberResList = crewQueryService.findCrewMember(crewId);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(findCrewMemberResList, "크루원 조회에 성공했습니다.");
    }

    @Operation(summary = "크루원 조회(크루장)", description = "크루원을 조회한다.")
    @GetMapping ("/master-member/{crew-id}")
    public CustomApiResponse<Object> findCrewMasterMember(@PathVariable("crew-id") long crewId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberKey = RequestUtils.getMemberKey(request);
        List<FindCrewMasterMemberRes> findCrewMasterMemberResList = crewQueryService.findCrewMasterMember(memberKey, crewId);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(findCrewMasterMemberResList, "크루원 조회에 성공했습니다.");
    }

    @Operation(summary = "크루 가입 요청", description = "크루 가입을 요청한다.")
    @PostMapping ("/join/{crew-id}")
    public CustomApiResponse<Object> joinCrewMember(@PathVariable("crew-id") long crewId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberKey = RequestUtils.getMemberKey(request);
        crewService.joinCrewMember(memberKey, crewId);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok("", "크루 가입 요청에 성공했습니다.");
    }

    @Operation(summary = "크루 가입 대기 조회", description = "크루 가입 대기 멤버를 조회한다.")
    @GetMapping ("/join/{crew-id}")
    public CustomApiResponse<Object> findWaitingCrewMember(@PathVariable("crew-id") long crewId, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberKey = RequestUtils.getMemberKey(request);
        List<FindWaitingCrewMemberRes> findWaitingCrewMemberResList = crewQueryService.findWaitingCrewMember(memberKey, crewId);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(findWaitingCrewMemberResList, "크루 가입 대기 조회에 성공했습니다.");
    }

    @Operation(summary = "크루 가입 승인/거절", description = "크루 가입을 승인/거절한다.")
    @PatchMapping ("/approve")
    public CustomApiResponse<Object> approveCrewMember(@Validated @RequestBody ApproveCrewReq approveCrewReq, HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberKey = RequestUtils.getMemberKey(request);
        crewService.approveCrewMember(ApproveCrewDto.of(memberKey, approveCrewReq));

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok("", "크루 가입 승인/거절에 성공했습니다.");
    }

    @Operation(summary = "크루 공지사항 등록", description = "크루 공지사항을 등록한다.")
    @PatchMapping ("/notice")
    public CustomApiResponse<Object> saveCrewNotice(@Validated @RequestBody SaveCrewNoticeReq saveCrewNoticeReq) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        crewService.saveCrewNotice(SaveCrewNoticeDto.of(saveCrewNoticeReq));

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok("", "크루 공지사항 등록에 성공했습니다.");
    }

    @Operation(summary = "크루 검색", description = "크루를 검색한다.")
    @GetMapping ("/search/{type}/{word}")
    public CustomApiResponse<Object> searchCrew(@PathVariable("type") int type, @PathVariable("word") String word) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        List<SearchCrewsRes> searchCrewsResList = crewQueryService.searchCrew(type, word);

        if (searchCrewsResList.isEmpty()) {
            log.info(logCurrent(getClassName(), getMethodName(), END));
            return CustomApiResponse.of(0, HttpStatus.NO_CONTENT, "크루 검색에 성공했습니다.");
        }

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(searchCrewsResList, "크루 검색에 성공했습니다.");
    }

}
