package com.plonit.plonitservice.api.badge.service.impl;

import com.plonit.plonitservice.api.badge.controller.response.FindBadgeRes;
import com.plonit.plonitservice.api.badge.service.BadgeQueryService;
import com.plonit.plonitservice.common.enums.BadgeStatus;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.exception.ErrorCode;
import com.plonit.plonitservice.domain.badge.repository.MemberBadgeQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;

@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeQueryServiceImpl implements BadgeQueryService {

    private final MemberBadgeQueryRepository memberBadgeQueryRepository;

    @Override
    public FindBadgeRes findMissionBadge(Long memberKey) {
        List<BadgeStatus> statusList = new ArrayList<>();
        statusList.add(BadgeStatus.COUNT);
        statusList.add(BadgeStatus.DISTANCE);

        return memberBadgeQueryRepository.findBadge(memberKey, statusList)
                .orElseThrow(() -> new CustomException(INVALID_FIELDS_REQUEST));
    }

    @Override
    public FindBadgeRes findRankingBadge(Long memberKey) {
        List<BadgeStatus> statusList = new ArrayList<>();
        statusList.add(BadgeStatus.RANKING);

        return memberBadgeQueryRepository.findBadge(memberKey, statusList)
                .orElseThrow(() -> new CustomException(INVALID_FIELDS_REQUEST));
    }
}
