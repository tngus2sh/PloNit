package com.plonit.floggingservice.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/flogging-service")
@RestController
@RequiredArgsConstructor
@Slf4j
public class FloggingApiController {
    
    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        log.info("server port = {}", request.getServerPort());
        return "This is Flogging-service message";
    }
}
