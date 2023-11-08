package com.plonit.ploggingservice.api.item.service.impl;

import com.plonit.ploggingservice.api.item.controller.response.FindTrashcanRes;
import com.plonit.ploggingservice.api.item.service.ItemService;
import com.plonit.ploggingservice.api.plogging.controller.SidoGugunFeignClient;
import com.plonit.ploggingservice.api.plogging.controller.response.KakaoAddressRes;
import com.plonit.ploggingservice.api.plogging.controller.response.SidoGugunCodeRes;
import com.plonit.ploggingservice.common.exception.CustomException;
import com.plonit.ploggingservice.common.util.KakaoPlaceUtils;
import com.plonit.ploggingservice.domain.item.repository.ItemInfoQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.plonit.ploggingservice.common.exception.ErrorCode.INVALID_PLACE_REQUEST;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final SidoGugunFeignClient sidoGugunFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final KakaoPlaceUtils kakaoPlaceUtils;
    private final ItemInfoQueryRepository itemInfoQueryRepository;

    @Override
    public List<FindTrashcanRes> findTrashcan(Double latitude, Double longitude) {

        KakaoAddressRes.Address address = kakaoPlaceUtils.getAddress(latitude, longitude);
        if (address == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }

        // 구군 코드 얻어오기
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        SidoGugunCodeRes sidoGugunCodeRes = circuitBreaker.run(
                () -> sidoGugunFeignClient.findSidoGugunCode(address.getRegion_1depth_name(), address.getRegion_2depth_name()).getResultBody(), // 통신하는 서비스
                throwable -> null // 에러 발생시 null 반환
        );

        if (sidoGugunCodeRes == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }

        List<FindTrashcanRes> result = itemInfoQueryRepository.findTrashcan(sidoGugunCodeRes.getGugunCode(), true);

        return result;
    }
}
