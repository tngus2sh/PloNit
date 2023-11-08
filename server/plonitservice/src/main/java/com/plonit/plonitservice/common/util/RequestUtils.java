package com.plonit.plonitservice.common.util;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
public class RequestUtils {
    public static Long getMemberKey(HttpServletRequest request) {
        String headerValue = request.getHeader("memberKey");
        if (headerValue == null || headerValue.isEmpty()) {
            return null;
        }
        return Long.parseLong(headerValue);
    }

    public static Long getMemberId() {
        Long memberKey = null;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        String headerValue = attributes.getRequest().getHeader("memberKey");
        if (headerValue != null) {
            try {
                memberKey = Long.parseLong(headerValue);  // Ensure it's a valid Long
            } catch (NumberFormatException e) {
                // Handle the case where user-id is not a valid Long
                System.err.println("memberKey header is not a valid Long: " + memberKey);
            }
        }
        return memberKey;
    }
}