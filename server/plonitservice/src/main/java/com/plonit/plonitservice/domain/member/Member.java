package com.plonit.plonitservice.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.plonit.plonitservice.api.member.controller.request.UpdateMemberReq;
import com.plonit.plonitservice.domain.TimeBaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@DynamicInsert
@DynamicUpdate
@Table(name = "members")
public class Member extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "members_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private long kakaoId;

    private String name;

    @Column(unique = true)
    private String nickname;

    @Setter
    private String profileImage;

    @ColumnDefault("false")
    private Boolean gender; // 0 : 남자 / 1 : 여자

    private String region;

    private float height;

    private float weight;

    private String id1365;

    private String birth;

    public void changeInfo(UpdateMemberReq updateMemberReq) {
        this.name = updateMemberReq.getName();
        this.nickname = updateMemberReq.getNickname();
        this.gender = Boolean.parseBoolean(updateMemberReq.getGender());
        this.birth = updateMemberReq.getBirth();
        this.region = updateMemberReq.getRegion();
        if(updateMemberReq.getId1365() != null) this.id1365 = updateMemberReq.getId1365();
        if(updateMemberReq.getHeight() != null) this.height = Float.parseFloat(updateMemberReq.getHeight());
        if(updateMemberReq.getWeight() != null) this.weight = Float.parseFloat(updateMemberReq.getWeight());
    }
}
