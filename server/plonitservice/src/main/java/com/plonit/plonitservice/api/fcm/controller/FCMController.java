package com.plonit.plonitservice.api.fcm.controller;

import com.plonit.plonitservice.api.crewping.controller.request.SaveCrewpingReq;
import com.plonit.plonitservice.api.fcm.controller.request.FCMCrewpingReq;
import com.plonit.plonitservice.api.fcm.controller.request.FCMHelpReq;
import com.plonit.plonitservice.api.fcm.controller.request.FCMReq;
import com.plonit.plonitservice.api.fcm.service.FCMService;
import com.plonit.plonitservice.common.CustomApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.START;

@Tag(name = "FCM", description = "FCM 알림")
@Slf4j
@RequestMapping("/api/plonit-service/v1/alarm")
@RestController
@RequiredArgsConstructor
public class FCMController {
    private final FCMService fcmService;
    @PostMapping
    public String sendFcm(@RequestBody FCMReq fcmReq) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info(fcmReq.toString());

        String message = fcmService.sendNotification(fcmReq);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return message;
    }

    @PostMapping("/help") // 주변 요청 도움 알림
    public String sendFcmHelp(@RequestBody FCMHelpReq fcmHelpReq) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info(fcmHelpReq.toString());

        String message = fcmService.sendHelp(fcmHelpReq);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return message;
    }

    @PostMapping("/crewping-end")
    public String sendFcmCrewEnd(@RequestBody FCMCrewpingReq fcmCrewpingReq) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        log.info(fcmCrewpingReq.toString());

        String message = fcmService.sendCrewEnd(fcmCrewpingReq);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return message;
    }
}
