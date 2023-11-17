package com.plonit.plonitservice.api.badge.service;


import com.plonit.plonitservice.api.badge.controller.response.FindBadgeRes;

import java.util.List;

public interface BadgeQueryService {

    public List<FindBadgeRes> findMissionBadge(Long memberKey);

    public List<FindBadgeRes> findRankingBadge(Long memberKey);

}
