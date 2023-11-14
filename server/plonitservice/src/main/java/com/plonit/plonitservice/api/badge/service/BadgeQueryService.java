package com.plonit.plonitservice.api.badge.service;


import com.plonit.plonitservice.api.badge.controller.response.FindBadgeRes;

public interface BadgeQueryService {

    public FindBadgeRes findMissionBadge(Long memberKey);

    public FindBadgeRes findRankingBadge(Long memberKey);

}
