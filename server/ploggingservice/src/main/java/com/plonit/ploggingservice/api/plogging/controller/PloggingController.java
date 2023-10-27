package com.plonit.ploggingservice.api.plogging.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/plogging-service/v1/plogging")
public class PloggingController {

    @Operation(summary = "get test", description = "test 설정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        log.info("server port = {}", request.getServerPort());
        return "Hi, there. This is a message from plogging-service";
    }

    @Operation(summary = "genkins test", description = "jenkins merge 테스트")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("/jenkins")
    public String jenkins(HttpServletRequest request) {
        return "jenkins CI/CD finish!";
    }
}
