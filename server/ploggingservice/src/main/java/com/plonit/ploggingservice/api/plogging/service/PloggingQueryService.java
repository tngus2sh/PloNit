package com.plonit.ploggingservice.api.plogging.service;

import com.plonit.ploggingservice.api.plogging.controller.response.PloggingHelpRes;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingLogRes;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingPeriodRes;

import java.util.List;

public interface PloggingQueryService {


    public List<PloggingPeriodRes> findPloggingLogByDay(String startDay, String endDay, Long memberKey);

    public PloggingLogRes findPloggingLogDetail(Long ploggingId, Long memberKey);

    public List<PloggingHelpRes> findPloggingHelp(Double latitude, Double longitude);
    
}
