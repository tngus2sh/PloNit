package com.plonit.ploggingservice.api.plogging.service;


import com.plonit.ploggingservice.api.plogging.controller.response.PloggingHelpRes;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingLogRes;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingPeriodRes;
import com.plonit.ploggingservice.api.plogging.service.dto.EndPloggingDto;
import com.plonit.ploggingservice.api.plogging.service.dto.HelpPloggingDto;
import com.plonit.ploggingservice.api.plogging.service.dto.StartPloggingDto;

import java.util.List;

public interface PloggingService {
    
    public Long saveStartPlogging(StartPloggingDto dto);
    
    public Long saveEndPlogging(EndPloggingDto dto);

    public List<PloggingPeriodRes> findPloggingLogByDay(String startDay, String endDay, Long memberKey);

    public PloggingLogRes findPloggingLogDetail(Long ploggingId, Long memberKey);

    public Long savePloggingHelp(HelpPloggingDto dto);
    
    public List<PloggingHelpRes> findPloggingHelp(Double latitude, Double longitude);
}
