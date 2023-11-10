package com.plonit.plonitservice.api.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.plonit.plonitservice.api.fcm.controller.request.FCMReq;
import com.plonit.plonitservice.common.util.RequestUtils;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.START;

@Service
@Slf4j
@RequiredArgsConstructor
public class FCMService {

    private final FirebaseMessaging firebaseMessaging;
    private final MemberRepository memberRepository;

    public String sendNotification(FCMReq request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberId = RequestUtils.getMemberId();
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isPresent()) {
            if(member.get().getFcmToken() != null) {
                Notification notification = Notification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getBody())
                        .setImage("images/logo.png")
                        .build();

                Message message = Message.builder()
                        .setToken(member.get().getFcmToken())
                        .setNotification(notification)
                        .build();

                try {
                    firebaseMessaging.send(message);
                    return "알림을 성공적으로 전송했습니다.";
                }
                catch(FirebaseMessagingException e) {
                    e.printStackTrace();
                    return "알림 전송을 실패했습니다.";
                }
            }
            else {
                return "서버에 해당 유저의 FirebaseToken이 존재하지 않습니다.";
            }
        }
        else {
            return "해당 유저가 존재하지 않습니다.";
        }
    }
}