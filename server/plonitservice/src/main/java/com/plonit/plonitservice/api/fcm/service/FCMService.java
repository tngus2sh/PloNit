package com.plonit.plonitservice.api.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.plonit.plonitservice.api.auth.controller.response.LogInRes;
import com.plonit.plonitservice.api.fcm.controller.request.FCMReq;
import com.plonit.plonitservice.common.util.RequestUtils;
import com.plonit.plonitservice.domain.fcm.repository.FCMTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.START;

@Slf4j
@RequiredArgsConstructor
@Service
public class FCMService{

    private final FCMTokenRepository fcmTokenRepository;

    public String sendNotification(FCMReq fcmReq) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        if (!hasKey(fcmReq.getTargetMemberId())) {
            log.info("서버에 해당 유저의 FirebaseToken 이 존재하지 않습니다.");
            return "서버에 해당 유저의 FirebaseToken 이 존재하지 않습니다.";
        }

        String token = getFcmToken(fcmReq.getTargetMemberId());
        Notification notification = Notification.builder()
                .setTitle(fcmReq.getTitle())
                .setBody(fcmReq.getBody())
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        send(message);
        log.info("알림을 성공적으로 전송했습니다.");
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return "알림을 성공적으로 전송했습니다.";
    }
    public void send(Message message) {
        FirebaseMessaging.getInstance().sendAsync(message);
    }

    public void saveToken(LogInRes logInRes) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        String fcmToken = RequestUtils.getFCMToken();

        if(fcmToken == null) {
            log.info("FCM TOKEN : NULL");
            log.info(logCurrent(getClassName(), getMethodName(), END));
            return;
        }

        log.info("FCM TOKEN : " + fcmToken);
        fcmTokenRepository.saveFcmToken(logInRes, fcmToken);
        log.info(logCurrent(getClassName(), getMethodName(), END));
    }

    public void deleteToken(Long memberIdl) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        fcmTokenRepository.deleteToken(memberIdl);
        log.info(logCurrent(getClassName(), getMethodName(), END));
    }

    private String getFcmToken(Long memberId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        String fcmToken = fcmTokenRepository.getFcmToken(memberId);
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return fcmToken;
    }

    private boolean hasKey(Long memberId) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        boolean hasKey = fcmTokenRepository.hasKey(memberId);
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return hasKey;
    }
}