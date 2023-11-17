package com.plonit.plonitservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {

    ACTIVE("크루핑 모집 중"),
    ONGOING("플로깅 중"),
    FINISH("플로깅 완료"),
    CANCEL("플로깅 취소");

    private final String text;

}
