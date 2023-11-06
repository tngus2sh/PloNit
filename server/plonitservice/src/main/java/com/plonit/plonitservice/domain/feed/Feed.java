package com.plonit.plonitservice.domain.feed;

import com.plonit.plonitservice.domain.TimeBaseEntity;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Feed extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "members_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "crew_id")
    private Crew crew;

    private String content;

    @ColumnDefault("false")
    private Boolean isDelete;

    public void changeDelete(){
        this.isDelete = true;
    }
}
