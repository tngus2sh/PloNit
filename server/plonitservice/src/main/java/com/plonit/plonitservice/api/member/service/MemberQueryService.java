package com.plonit.plonitservice.api.member.service;

import com.plonit.plonitservice.api.member.controller.response.FindCrewpingInfoRes;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MemberQueryService {

    List<FindCrewpingInfoRes> findCrewpingInfo();
}
