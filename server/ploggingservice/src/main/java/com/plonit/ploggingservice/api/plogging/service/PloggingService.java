package com.plonit.ploggingservice.api.plogging.service;


import com.plonit.ploggingservice.api.plogging.service.dto.EndPloggingDto;
import com.plonit.ploggingservice.api.plogging.service.dto.HelpPloggingDto;
import com.plonit.ploggingservice.api.plogging.service.dto.ImagePloggingDto;
import com.plonit.ploggingservice.api.plogging.service.dto.StartPloggingDto;

public interface PloggingService {
    
    Long saveStartPlogging(StartPloggingDto dto);
    
    Long saveEndPlogging(EndPloggingDto dto);

    Long savePloggingHelp(HelpPloggingDto dto);

    String savePloggingImage(ImagePloggingDto dto);

}
