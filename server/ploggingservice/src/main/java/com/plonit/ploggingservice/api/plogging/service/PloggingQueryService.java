package com.plonit.ploggingservice.api.plogging.service;

import com.plonit.ploggingservice.api.plogging.controller.response.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface PloggingQueryService {

    List<PloggingPeriodRes> findPloggingLogByDay(String startDay, String endDay, Long memberKey, String type);

    List<PloggingMonthRes> findPloggingLogByMonth(int month, Long memberKey);

    PloggingLogRes findPloggingLogDetail(Long ploggingId, Long memberKey);

    List<PloggingHelpRes> findPloggingHelp(Double latitude, Double longitude);

    List<UsersRes> findPloggingUsers(Double latitude, Double longitude);

    Integer countMemberPlogging();

    HashMap<Long, Long> countCrewPlogging();
    
}
