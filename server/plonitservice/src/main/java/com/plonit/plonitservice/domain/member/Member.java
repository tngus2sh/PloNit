package com.plonit.plonitservice.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.plonit.plonitservice.domain.TimeBaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@DynamicInsert
@DynamicUpdate
public class Member extends TimeBaseEntity {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NonNull
    private String email;

//    @NonNull
//    private String name;
//
//    @NonNull
//    @Column(unique = true)
//    private String nickname;
//
//    private String profileImage;
//
//    @ColumnDefault("false")
//    private boolean gender;
//
//    @NonNull
//    private String region;

    private float height;

    private float weight;

    private String id1365;

}
