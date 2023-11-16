package com.plonit.ploggingservice.api.plogging.controller.request;

import com.plonit.ploggingservice.api.plogging.controller.CrewpingWebSocketController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewpingMessageReq {

    public enum MessageType {
        START, END, LOCATION, WAIT, EXIT
    }

    private MessageType type;
    private String nickName;
    // 보내는 사람 : 사용자 아이디
    private String profileImage;
    private String senderId;
    // 내용
    private String message;
    private String roomId;
    private Double latitude;
    private Double longitude;
    private List<Members> members = new ArrayList<>();
}
