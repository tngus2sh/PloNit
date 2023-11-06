package com.plonit.plonitservice.api.badge.service.dto;

import com.plonit.plonitservice.api.badge.controller.request.CrewBadgeReq;
import com.plonit.plonitservice.domain.badge.Badge;
import com.plonit.plonitservice.domain.badge.CrewBadge;
import com.plonit.plonitservice.domain.crew.Crew;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrewBadgeDto {
    private Long crewId;

    private Long badgeId;

    public static CrewBadgeDto of(CrewBadgeReq crewBadgeReq) {
        return CrewBadgeDto.builder()
                .crewId(crewBadgeReq.getCrewId())
                .badgeId(crewBadgeReq.getBadgeId())
                .build();
    }

    public static CrewBadge toEntity(Badge badge, Crew crew) {
        return CrewBadge.builder()
                .badge(badge)
                .crew(crew)
                .build();
    }
}
