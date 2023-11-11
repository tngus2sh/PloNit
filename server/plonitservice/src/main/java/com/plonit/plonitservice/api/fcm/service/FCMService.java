package com.plonit.plonitservice.api.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.plonit.plonitservice.api.auth.controller.response.LogInRes;
import com.plonit.plonitservice.api.fcm.controller.request.FCMReq;
import com.plonit.plonitservice.common.util.RequestUtils;
import com.plonit.plonitservice.domain.fcm.repository.FCMTokenRepository;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            return "서버에 해당 유저의 FirebaseToken이 존재하지 않습니다.";
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

//        Message message = Message.builder()
//                .putData("title", fcmReq.getTitle())
//                .putData("content", fcmReq.getBody())
//                .setToken(token)
//                .build();

        send(message);
        return "알림을 성공적으로 전송했습니다.";
    }
    public void send(Message message) {
        FirebaseMessaging.getInstance().sendAsync(message);
    }

    public void saveToken(LogInRes logInRes) {
        fcmTokenRepository.saveFcmToken(logInRes);
    }

    public void deleteToken(Long memberIdl) {
        fcmTokenRepository.deleteToken(memberIdl);
    }

    private String getFcmToken(Long memberId) {
        return fcmTokenRepository.getFcmToken(memberId);
    }

    private boolean hasKey(Long memberId) {
        return fcmTokenRepository.hasKey(memberId);
    }
}