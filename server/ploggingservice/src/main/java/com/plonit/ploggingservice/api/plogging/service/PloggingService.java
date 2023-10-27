package com.plonit.ploggingservice.api.plogging.service;

import com.plonit.ploggingservice.api.plogging.controller.request.StartPloggingRequest;

public interface PloggingService {
    
    public Long saveStartPlogging(StartPloggingRequest request);
    
}
