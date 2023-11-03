package com.plonit.plonitservice.api.region.service.impl;

import com.plonit.plonitservice.api.region.controller.response.DongRes;
import com.plonit.plonitservice.api.region.controller.response.GugunRes;
import com.plonit.plonitservice.api.region.controller.response.SidoRes;
import com.plonit.plonitservice.api.region.service.RegionQueryService;
import com.plonit.plonitservice.domain.region.repository.RegionQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionQueryServiceImpl implements RegionQueryService {
    
    private final RegionQueryRepository regionQueryRepository;
    
    @Override
    public List<SidoRes> findSido() {
        return regionQueryRepository.findSido();
    }

    @Override
    public List<GugunRes> findGugun(Long sidoCode) {
        return regionQueryRepository.findGugun(sidoCode);
    }

    @Override
    public List<DongRes> findDong(Long gugunCode) {
        return regionQueryRepository.findDong(gugunCode);
    }
}
