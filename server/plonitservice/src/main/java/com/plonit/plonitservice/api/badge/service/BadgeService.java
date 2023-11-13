package com.plonit.plonitservice.api.badge.service;

import com.plonit.plonitservice.api.badge.service.dto.*;
import com.plonit.plonitservice.common.enums.BadgeCode;

import java.util.List;

public interface BadgeService {

    public void saveBadge(List<BadgeDto> badgeDtos);

    public void saveBadgeByIndividual(List<MembersBadgeDto> membersBadgeDtos);

    public void saveBadgeByCrew(List<CrewBadgeDto> crewBadgeDtos);

    public void grantBadgeByIndividual(GrantMemberBadgeDto grantMemberBadgeDto);

    public void grantRankBadge(GrantMemberRankDto grantMemberRankDto);

    public void grantCrewRankBadge(GrantCrewRankDto grantCrewRankDto);

    public void doGrantCountBadge(int value, Long memberKey);

    public void doGrantDistanceBadge(Double distance, Long memberKey);

    public void saveMemberBadge(BadgeCode code, Long memberKey);

    public void saveMemberBadgeByDistance(BadgeCode code, Long memberKey);

    public void saveCrewBadge(BadgeCode code, Long crewId);
}
