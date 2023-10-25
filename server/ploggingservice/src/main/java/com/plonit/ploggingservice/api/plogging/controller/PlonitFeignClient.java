package com.plonit.ploggingservice.api.plogging.controller;

import com.plonit.ploggingservice.common.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "plonit-service")
public interface PlonitFeignClient {
    
    @GetMapping("/plonit-service/api/test")
    ApiResponse<List<String>> getTest();
    
    
}
