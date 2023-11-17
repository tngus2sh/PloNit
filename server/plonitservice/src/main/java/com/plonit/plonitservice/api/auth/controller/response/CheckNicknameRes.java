package com.plonit.plonitservice.api.auth.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CheckNicknameRes {
    private boolean avl;

    public static CheckNicknameRes of(boolean avl) {
        return CheckNicknameRes.builder()
                .avl(avl)
                .build();
    }
}
