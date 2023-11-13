package com.plonit.plonitservice.api.member.service;

import com.plonit.plonitservice.api.member.controller.response.VolunteerMemberInfoRes;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MemberQueryService {

    List<VolunteerMemberInfoRes> findVolunteerInfo(List<Long> memberIdList);

}
