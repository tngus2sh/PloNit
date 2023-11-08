package com.plonit.plonitservice.api.crew.controller.response;

import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.CrewMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class FindCrewRes {
    private long id;
    private String name;
    private int cntPeople;
    private String crewImage;
    private String region;
    private String introduce;

    // todo : ranking 추가
    private String rankingInfo;
    private int totalRanking;
    private int avgRanking;

    private String crewMasterProfileImage;
    private String crewMasterNickname;
    private Boolean isCrewMaster;
    private String notice;
    public FindCrewRes (Long id, String name, Integer cntPeople, String crewImage,
                       String region, String introduce, String notice,
                       String crewMasterProfileImage, String crewMasterNickname, Boolean isCrewMaster) {
        this.id = id;
        this.name = name;
        this.cntPeople = cntPeople;
        this.crewImage = crewImage;
        this.region = region;
        this.introduce = introduce;
        this.notice = notice;
        this.crewMasterProfileImage = crewMasterProfileImage;
        this.crewMasterNickname = crewMasterNickname;
        this.isCrewMaster = isCrewMaster;
    }
}
