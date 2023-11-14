package com.plonit.plonitservice.api.member.controller;

import com.plonit.plonitservice.api.member.controller.request.UpdateMemberReq;
import com.plonit.plonitservice.api.member.controller.response.FindCrewInfoRes;
import com.plonit.plonitservice.api.member.controller.response.FindMemberInfoRes;
import com.plonit.plonitservice.api.member.controller.response.FindMemberRes;
import com.plonit.plonitservice.api.member.service.MemberService;
import com.plonit.plonitservice.api.member.service.dto.UpdateMemberDto;
import com.plonit.plonitservice.common.CustomApiResponse;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.util.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;
import static com.plonit.plonitservice.common.util.LogCurrent.*;


@Tag(name = "Member API Controller", description = "Member API Document")
@Slf4j
@RequestMapping("/api/plonit-service/v1/members")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "사용자 정보 수정", description = "사용자는 정보를 수정할 수 있다.")
    @PutMapping // 사용자 정보 수정
    public CustomApiResponse<Object> updateMemberInfo(@ModelAttribute UpdateMemberReq updateMemberReq, HttpServletRequest request, Errors errors) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info(updateMemberReq.toString());

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("error message : " + e.getDefaultMessage());
            });
            log.info(logCurrent(getClassName(), getMethodName(), END));
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        Long memberKey = RequestUtils.getMemberKey(request);
        log.info("memberKey : " + memberKey);

        FindMemberRes findMemberRes = memberService.updateMember(UpdateMemberDto.of(memberKey, updateMemberReq));
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(findMemberRes);
    }

    @GetMapping()
    public CustomApiResponse<FindMemberInfoRes> findMemberInfo() {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("FindMemberInfo");

        FindMemberInfoRes response = memberService.findMemberInfo();

        return CustomApiResponse.ok(response, "내 정보 조회에 성공했습니다.");
    }

    @Operation(summary = "내 크루 조회", description = "사용자는 내 크루 정보를 조회할 수 있다.")
    @GetMapping("/crew")
    public CustomApiResponse<Object> findCrewInfo() {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info("FindMemberInfo");

        List<FindCrewInfoRes> response = memberService.findCrewInfo();
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(response, "내 크루 정보 조회에 성공했습니다.");
    }
}