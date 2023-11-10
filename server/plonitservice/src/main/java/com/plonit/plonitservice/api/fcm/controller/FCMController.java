package com.plonit.plonitservice.api.fcm.controller;

import com.plonit.plonitservice.api.crewping.controller.request.SaveCrewpingReq;
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

@Tag(name = "FCM", description = "FCM 알림")
@Slf4j
@RequestMapping("/api/plonit-service/v1/fcm")
@RestController
@RequiredArgsConstructor
public class FCMController {
    private final FCMService firebaseCloudMessageService;
    @PostMapping
    public String sendFcm(@RequestBody FCMReq fcmReq) {
        return firebaseCloudMessageService.sendNotification(fcmReq);
    }
}
