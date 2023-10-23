package com.plonit.plonitservice.api.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/plonit-service/member")
@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberApiController {
    
    @GetMapping("/test")
    public String check(HttpServletRequest request) {
        log.info("server port = {}", request.getServerPort());
        return "Hi, there. This is a message from plonit-service";
    }
}
