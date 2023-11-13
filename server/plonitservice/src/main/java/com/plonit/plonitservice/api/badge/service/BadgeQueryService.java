package com.plonit.plonitservice.api.badge.service;


import com.plonit.plonitservice.api.badge.controller.response.FindBadgeRes;

public interface BadgeQueryService {

    public FindBadgeRes findBadge(Long memberKey);

}
