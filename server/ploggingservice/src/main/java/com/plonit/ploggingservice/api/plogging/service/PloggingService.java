package com.plonit.ploggingservice.api.plogging.service;


import com.plonit.ploggingservice.api.plogging.service.dto.EndPloggingDto;
import com.plonit.ploggingservice.api.plogging.service.dto.HelpPloggingDto;
import com.plonit.ploggingservice.api.plogging.service.dto.ImagePloggingDto;
import com.plonit.ploggingservice.api.plogging.service.dto.StartPloggingDto;

public interface PloggingService {
    
    public Long saveStartPlogging(StartPloggingDto dto);
    
    public Long saveEndPlogging(EndPloggingDto dto);

    public Long savePloggingHelp(HelpPloggingDto dto);

    public Long savePloggingImage(ImagePloggingDto dto);
}
