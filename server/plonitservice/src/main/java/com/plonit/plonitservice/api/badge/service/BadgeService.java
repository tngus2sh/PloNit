package com.plonit.plonitservice.api.badge.service;

import com.plonit.plonitservice.api.badge.service.dto.BadgeDto;
import com.plonit.plonitservice.api.badge.service.dto.CrewBadgeDto;
import com.plonit.plonitservice.api.badge.service.dto.MembersBadgeDto;

import java.util.List;

public interface BadgeService {

    public void saveBadge(List<BadgeDto> dtos);

    public void saveBadgeByIndividual(List<MembersBadgeDto> dtos);

    public void saveBadgeByCrew(List<CrewBadgeDto> dtos);

}
