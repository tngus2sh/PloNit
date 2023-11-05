package com.plonit.plonitservice.api.badge.service.impl;

import com.plonit.plonitservice.api.badge.service.BadgeService;
import com.plonit.plonitservice.api.badge.service.dto.BadgeDto;
import com.plonit.plonitservice.api.badge.service.dto.CrewBadgeDto;
import com.plonit.plonitservice.api.badge.service.dto.MembersBadgeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BadgeServiceImpl implements BadgeService {

    @Override
    public void saveBadge(List<BadgeDto> dtos) {

    }

    @Override
    public void saveBadgeByIndividual(List<MembersBadgeDto> dtos) {

    }

    @Override
    public void saveBadgeByCrew(List<CrewBadgeDto> dtos) {

    }
}
