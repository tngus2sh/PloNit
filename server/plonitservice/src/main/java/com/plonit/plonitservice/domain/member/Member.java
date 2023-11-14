package com.plonit.plonitservice.domain.member;

import com.plonit.plonitservice.api.member.service.dto.UpdateMemberDto;
import com.plonit.plonitservice.domain.TimeBaseEntity;
import com.plonit.plonitservice.domain.region.Dong;
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

    private Long dongCode;

    private String region;

    private float height;

    private float weight;

    private String id1365;

    private String birth;

    public void changeInfo(UpdateMemberDto updateMemberDto, Dong dong) {
        StringBuilder sb = new StringBuilder();

        this.name = updateMemberDto.getName();
        this.nickname = updateMemberDto.getNickname();
        this.gender = updateMemberDto.getGender();
        this.birth = updateMemberDto.getBirth();
        this.dongCode = updateMemberDto.getDongCode();

        sb.append(dong.getGugun().getSido().getName() + " " +
                dong.getGugun().getName() + " " +
                dong.getName());
        this.region = sb.toString();

        if(updateMemberDto.getId1365() != null) this.id1365 = updateMemberDto.getId1365();
        if(updateMemberDto.getHeight() != null) this.height = Float.parseFloat(updateMemberDto.getHeight());
        if(updateMemberDto.getWeight() != null) this.weight = Float.parseFloat(updateMemberDto.getWeight());
    }
}
