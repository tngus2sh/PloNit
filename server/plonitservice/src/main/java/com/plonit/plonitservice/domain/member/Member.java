package com.plonit.plonitservice.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.plonit.plonitservice.domain.TimeBaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "members")
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
    @Column(name = "members_id")
    private Long id;

}
