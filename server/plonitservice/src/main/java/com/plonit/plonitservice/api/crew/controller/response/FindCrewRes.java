package com.plonit.plonitservice.api.crew.controller.response;

import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.CrewMember;
import lombok.*;

@Data
@AllArgsConstructor
@Setter
public class FindCrewRes {
    private long id;
    private String name;
    private long cntPeople;
    private String crewImage;
    private String region;
    private String introduce;
    private String notice;

    private String crewMasterProfileImage;
    private String crewMasterNickname;
    private Boolean isCrewMaster;
    private Boolean isMyCrew;
    private Boolean isWaiting;
    private String startDate;
    private String endDate;
    private int totalRanking;
    private int avgRanking;
    private Double totalDistance;
    private Double avgDistance;

    public FindCrewRes (Long id, String name, Long cntPeople, String crewImage,
                       String region, String introduce, String notice,
                       String crewMasterProfileImage, String crewMasterNickname, Boolean isCrewMaster, Boolean isMyCrew, Boolean isWaiting) {
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
        this.isMyCrew = isMyCrew;
        this.isWaiting = isWaiting;
    }
}
