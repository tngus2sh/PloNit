package com.plonit.ploggingservice.api.plogging.service;


import com.plonit.ploggingservice.api.plogging.service.dto.StartPloggingDto;

public interface PloggingService {
    
    public Long saveStartPlogging(StartPloggingDto dto);
    
}
