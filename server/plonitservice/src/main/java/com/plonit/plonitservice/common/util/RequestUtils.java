package com.plonit.plonitservice.common.util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
@Slf4j
public class RequestUtils {
    public static Long getMemberKey(HttpServletRequest request) {
        String headerValue = request.getHeader("memberKey");
        if (headerValue == null || headerValue.isEmpty()) {
            return null;
        }
        return Long.parseLong(headerValue);
    }

    public static Long getMemberId() {
        Long memberId = null;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        String headerValue = attributes.getRequest().getHeader("memberKey");
        if (headerValue != null) {
            try {
                memberId = Long.parseLong(headerValue);
            } catch (NumberFormatException e) {
                log.debug("memberKey header is not a valid Long: " + memberId);
            }
        }
        return memberId;
    }

    public static String getToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        return attributes.getRequest().getHeader("accessToken");
    }
}