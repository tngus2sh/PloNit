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
    private boolean registeredMember;
    private TokenInfoRes tokenInfo;
}
