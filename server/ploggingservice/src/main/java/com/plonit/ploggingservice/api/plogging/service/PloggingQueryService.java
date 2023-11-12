package com.plonit.ploggingservice.api.plogging.service;

import com.plonit.ploggingservice.api.plogging.controller.response.PloggingHelpRes;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingLogRes;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingPeriodRes;

import java.util.List;

public interface PloggingQueryService {

    List<PloggingPeriodRes> findPloggingLogByDay(String startDay, String endDay, Long memberKey);

    PloggingLogRes findPloggingLogDetail(Long ploggingId, Long memberKey);

    List<PloggingHelpRes> findPloggingHelp(Double latitude, Double longitude);

    Integer countMemberPlogging();
    
}
