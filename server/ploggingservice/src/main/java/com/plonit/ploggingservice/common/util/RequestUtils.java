package com.plonit.ploggingservice.common.util;
import javax.servlet.http.HttpServletRequest;
public class RequestUtils {
    public static Long getMemberKey(HttpServletRequest request) {
        String headerValue = request.getHeader("memberKey");
        if (headerValue == null || headerValue.isEmpty()) {
            return null;
        }
        return Long.parseLong(headerValue);
    }
}