package com.plonit.plonitservice.domain.crew;

import com.plonit.plonitservice.domain.TimeBaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Crew extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crew_id")
    private Long id;

    private String name;

    @ColumnDefault("1")
    private Integer cntPeople;

    @Column(length = 500)
    private String crewImage;

    @Column(length = 500)
    private String introduce;

    private String notice;

    private Long gugunCode;

    private String region;

    public void changeNotice(String notice) {
        this.notice = notice;
    }
}
