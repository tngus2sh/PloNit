package com.plonit.ploggingservice.domain.plogging;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Group extends TimeBaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "plogging_id", nullable = false)
    private Plogging plogging;
    
    @Column
    private Long crewpingKey;

    @Builder
    public Group(Long id, Plogging plogging, Long crewpingKey) {
        this.id = id;
        this.plogging = plogging;
        this.crewpingKey = crewpingKey;
    }
}
