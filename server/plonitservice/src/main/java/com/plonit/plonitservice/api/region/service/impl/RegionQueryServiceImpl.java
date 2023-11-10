package com.plonit.plonitservice.api.region.service.impl;

import com.plonit.plonitservice.api.region.controller.response.DongRes;
import com.plonit.plonitservice.api.region.controller.response.GugunRes;
import com.plonit.plonitservice.api.region.controller.response.SidoGugunCodeRes;
import com.plonit.plonitservice.api.region.controller.response.SidoRes;
import com.plonit.plonitservice.api.region.service.RegionQueryService;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.exception.ErrorCode;
import com.plonit.plonitservice.domain.region.repository.RegionQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.plonit.plonitservice.common.exception.ErrorCode.REGION_NOT_FOUND;

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

    @Override
    public SidoGugunCodeRes findSidogugunCode(String sidoName, String gugunName) {
        return regionQueryRepository.findSidoGugunCode(sidoName, gugunName)
                .orElseThrow(() -> new CustomException(REGION_NOT_FOUND));
    }
}
