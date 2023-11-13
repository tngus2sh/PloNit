package com.plonit.plonitservice.api.badge.service.impl;

import com.plonit.plonitservice.api.badge.controller.response.FindBadgeRes;
import com.plonit.plonitservice.api.badge.service.BadgeQueryService;
import com.plonit.plonitservice.domain.badge.repository.MemberBadgeQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeQueryServiceImpl implements BadgeQueryService {

    private final MemberBadgeQueryRepository memberBadgeQueryRepository;

    @Override
    public FindBadgeRes findBadge(Long memberKey) {

        return null;
    }
}
