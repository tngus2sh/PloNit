package com.plonit.plonitservice.api.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.plonit.plonitservice.api.auth.controller.response.FCMRes;
import com.plonit.plonitservice.api.auth.controller.response.LogInRes;
import com.plonit.plonitservice.api.fcm.controller.request.FCMCrewpingReq;
import com.plonit.plonitservice.api.fcm.controller.request.FCMHelpReq;
import com.plonit.plonitservice.api.fcm.controller.request.FCMReq;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.util.RequestUtils;
import com.plonit.plonitservice.domain.crewping.repository.CrewpingMemberQueryRepository;
import com.plonit.plonitservice.domain.fcm.repository.FCMTokenRepository;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import com.plonit.plonitservice.domain.region.Gugun;
import com.plonit.plonitservice.domain.region.repository.GugunRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.plonit.plonitservice.common.exception.ErrorCode.GUGUN_NOT_FOUND;
import static com.plonit.plonitservice.common.exception.ErrorCode.USER_BAD_REQUEST;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.START;

@Slf4j
@RequiredArgsConstructor
@Service
public class FCMService{

    private final FCMTokenRepository fcmTokenRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    private final CrewpingMemberQueryRepository crewpingMemberQueryRepository;
    private final GugunRepository gugunRepository;
    public String sendNotification(FCMReq fcmReq) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        if (!hasKey(fcmReq.getTargetMemberId())) {
            log.info("서버에 해당 유저의 FirebaseToken 이 존재하지 않습니다.");
            return "서버에 해당 유저의 FirebaseToken 이 존재하지 않습니다.";
        }

        String token = getFcmToken(fcmReq.getTargetMemberId());
        if(token.isEmpty()) {
            log.info("서버에 해당 유저의 FirebaseToken 이 유효하지 않습니다.");
            return "서버에 해당 유저의 FirebaseToken 이 유효하지 않습니다.";
        }

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

    @Transactional
    public String sendHelp(FCMHelpReq fcmHelpReq) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        Long memberId = RequestUtils.getMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(USER_BAD_REQUEST));
        Gugun gugun = gugunRepository.findByCode(fcmHelpReq.getGugunCode())
                .orElseThrow(() -> new CustomException(GUGUN_NOT_FOUND));

        List<Long> nearMemberIds = memberQueryRepository.findByGugunCode(fcmHelpReq.getGugunCode());

        for(Long item : nearMemberIds) {
            if(!hasKey(item)) continue;
            if(item == memberId) continue;
            String token = getFcmToken(item);
            if(token.isEmpty()) continue;
            Notification notification = Notification.builder()
                    .setTitle("HELP")
                    .setBody(makeHelpString(gugun.getName(), member.getNickname()))
                    .build();
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .build();
            send(message);
            log.info(item + " member 에게 알림을 성공적으로 전송했습니다.");
        }

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return "알림을 성공적으로 전송했습니다.";
    }

    public String sendCrewEnd(FCMCrewpingReq fcmCrewpingReq) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        List<Long> nearMemberIds = crewpingMemberQueryRepository.findByCrewpingId(fcmCrewpingReq.getCrewpingId());

        for(Long item : nearMemberIds) {
            if(!hasKey(item)) continue;
            String token = getFcmToken(item);
            if(token.isEmpty()) continue;
            Notification notification = Notification.builder()
                    .setTitle("CREWPING_END")
                    .setBody(makeCrewEndString(fcmCrewpingReq.getCrewName(), fcmCrewpingReq.getCrewpingName()))
                    .build();
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .build();
            send(message);
            log.info(item + " member 에게 알림을 성공적으로 전송했습니다.");
        }

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return "알림을 성공적으로 전송했습니다.";
    }

    @Transactional
    public void saveToken(FCMRes fcmRes) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        Long memberId = RequestUtils.getMemberId();

        if(fcmRes.getFcmToken() == null) {
            log.info("FCM TOKEN : NULL");
            log.info(logCurrent(getClassName(), getMethodName(), END));
            return;
        }

        log.info("FCM TOKEN : " + fcmRes.getFcmToken());
        fcmTokenRepository.saveFcmToken(memberId, fcmRes.getFcmToken());
        log.info(logCurrent(getClassName(), getMethodName(), END));
    }

    @Transactional
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

    private String makeHelpString(String gugum, String memberId) {
        StringBuilder sb = new StringBuilder();
        sb.append(gugum + "");
        sb.append("의 ");
        sb.append(memberId);
        sb.append("님이 주변 사용자에게 도움을 요청했습니다.");
        return sb.toString();
    }

    private String makeCrewEndString(String crew, String crewpring) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"" +crew + "\"");
        sb.append(" 크루의 ");
        sb.append("\"" + crewpring + "\"");
        sb.append(" 플로깅이 종료되었습니다.");
        return sb.toString();
    }
}