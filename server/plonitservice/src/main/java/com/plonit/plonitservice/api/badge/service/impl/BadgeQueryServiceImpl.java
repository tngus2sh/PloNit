package com.plonit.plonitservice.api.badge.service.impl;

import com.plonit.plonitservice.api.badge.controller.response.FindBadgeRes;
import com.plonit.plonitservice.api.badge.service.BadgeQueryService;
import com.plonit.plonitservice.common.enums.BadgeStatus;
import com.plonit.plonitservice.domain.badge.repository.MemberBadgeQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeQueryServiceImpl implements BadgeQueryService {

    private final MemberBadgeQueryRepository memberBadgeQueryRepository;

    @Override
    public FindBadgeRes findBadge(Long memberKey) {
        List<BadgeStatus> statusList = new ArrayList<>();
        statusList.add(BadgeStatus.COUNT);
        statusList.add(BadgeStatus.DISTANCE);

        return memberBadgeQueryRepository.findBadge(memberKey, statusList);
    }
}
