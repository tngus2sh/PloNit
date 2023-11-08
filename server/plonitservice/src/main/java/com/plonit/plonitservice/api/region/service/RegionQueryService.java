package com.plonit.plonitservice.api.region.service;

import com.plonit.plonitservice.api.region.controller.response.DongRes;
import com.plonit.plonitservice.api.region.controller.response.GugunRes;
import com.plonit.plonitservice.api.region.controller.response.SidoGugunCodeRes;
import com.plonit.plonitservice.api.region.controller.response.SidoRes;

import java.util.List;

public interface RegionQueryService {

    public List<SidoRes> findSido();
    
    public List<GugunRes> findGugun(Long sidoCode);
    
    public List<DongRes> findDong(Long gugunCode);

    public SidoGugunCodeRes findSidogugunCode(String sidoName, String gugunName);
}
