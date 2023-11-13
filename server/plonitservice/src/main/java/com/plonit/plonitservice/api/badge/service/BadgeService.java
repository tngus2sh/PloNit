package com.plonit.plonitservice.api.badge.service;

import com.plonit.plonitservice.api.badge.service.dto.BadgeDto;
import com.plonit.plonitservice.api.badge.service.dto.CrewBadgeDto;
import com.plonit.plonitservice.api.badge.service.dto.GrantMemberBadgeDto;
import com.plonit.plonitservice.api.badge.service.dto.MembersBadgeDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BadgeService {

    public void saveBadge(List<BadgeDto> badgeDtos);

    public void saveBadgeByIndividual(List<MembersBadgeDto> membersBadgeDtos);

    public void saveBadgeByCrew(List<CrewBadgeDto> crewBadgeDtos);

    public void grantBadgeByIndividual(GrantMemberBadgeDto grantMemberBadgeDto);

}
