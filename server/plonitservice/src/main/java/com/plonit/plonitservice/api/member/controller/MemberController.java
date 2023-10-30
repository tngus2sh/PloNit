package com.plonit.plonitservice.api.member.controller;

import com.plonit.plonitservice.api.member.controller.request.UpdateMemberReq;
import com.plonit.plonitservice.api.member.service.MemberService;
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


@Tag(name = "Test", description = "설명")
@Slf4j
@RequestMapping("/plonit-service/v1/members")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @PutMapping("/{userId}") // 사용자 정보 수정
    public CustomApiResponse<Object> updateMemberInfo(@PathVariable("userId") Long userId, @ModelAttribute UpdateMemberReq memberUpdateReq, HttpServletRequest request, Errors errors) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("error message : " + e.getDefaultMessage());
            });
            log.info(logCurrent(getClassName(), getMethodName(), END));
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        Long memberKey = RequestUtils.getMemberKey(request);
        log.info("memberKey : " + memberKey);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(memberService.updateMember(userId, memberUpdateReq));
    }
}

