package com.plonit.plonitservice.api.auth.controller.response;

import com.plonit.plonitservice.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class LogInRes {
    private long id;
    private String email;
    private String nickname;

    public static LogInRes of(Member member) {
        return LogInRes.builder()
                .email(member.getEmail())
                .build();
    }
}
