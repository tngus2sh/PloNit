package com.plonit.ploggingservice.api.plogging.service;


import com.plonit.ploggingservice.api.plogging.service.dto.*;

public interface PloggingService {

    void test(int num);
    
    Long saveStartPlogging(StartPloggingDto dto);
    
    Long saveEndPlogging(EndPloggingDto dto);

    Long savePloggingHelp(HelpPloggingDto dto);

    String savePloggingImage(ImagePloggingDto dto);

    Long saveVolunteerData(VolunteerPloggingDto dto);

    Long updatePloggingHelpStatus(UpdatePloggingHelpStatusDto dto);

}
