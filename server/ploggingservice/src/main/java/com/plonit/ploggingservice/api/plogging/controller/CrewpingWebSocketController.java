package com.plonit.ploggingservice.api.plogging.controller;

import com.plonit.ploggingservice.api.plogging.controller.request.CrewpingMessageReq;
import com.plonit.ploggingservice.api.plogging.controller.request.Members;
import com.plonit.ploggingservice.common.util.RedisUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CrewpingWebSocketController {
    private final SimpMessageSendingOperations sendingOperations;
    private final RedisUtils redisUtils;


    @MessageMapping("/chat/message")
    public ResponseEntity<Void> enter(@RequestBody CrewpingMessageReq request) {
        log.info("[WEBSOCKET] start");
        log.info(request.toString());
        String roomId = request.getRoomId();

        /* 상태값이 WAIT일 때, 사용자들의 정보 수집 */
        if (CrewpingMessageReq.MessageType.WAIT.equals(request.getType())) {
            /* 레디스에서 기존에 있는 사용자들 목록 불러오기 */
            Map<String, String> crewpingMembers = redisUtils.getRedisHash(request.getRoomId());

            // 레디스에 값 생성
            redisUtils.setRedisHash(request.getRoomId(), request.getNickName(), request.getProfileImage(), (long) (60 * 60 * 24));

            List<Members> members = new ArrayList<>();
            for (String name : crewpingMembers.keySet()) {
                String nickName = name;
                String image = crewpingMembers.get(name);
                members.add(Members.builder()
                        .nickName(nickName)
                        .profileImage(image)
                        .build());
            }
            request.setMembers(members);
            request.setMessage(request.getNickName() + "님이 입장하셨습니다.");
        }

        // topic-1대다, queue-1대1
        sendingOperations.convertAndSend("/topic/chat/room/" + roomId, request);
        return ResponseEntity.ok(null);
    }
}
