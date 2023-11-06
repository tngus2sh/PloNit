package com.plonit.plonitservice.api.badge.controller.request;

import lombok.Data;

@Data
public class CrewBadgeReq {

    private Long crewId;

    private Long badgeId;
}
