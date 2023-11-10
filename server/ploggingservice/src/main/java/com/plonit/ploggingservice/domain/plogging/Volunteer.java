package com.plonit.ploggingservice.domain.plogging;

import com.plonit.ploggingservice.common.enums.Finished;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Volunteer extends TimeBaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "volunteer_id")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "plogging_id", nullable = false)
    private Plogging plogging;
    
    @Column
    private Long sidoKey;
    
    @Column
    private Long gugunKey;
    
    @Column
    @Enumerated(EnumType.STRING)
    private Finished finished;

    @Builder
    public Volunteer(Long id, Plogging plogging, Long sidoKey, Long gugunKey, Finished finished) {
        this.id = id;
        this.plogging = plogging;
        this.sidoKey = sidoKey;
        this.gugunKey = gugunKey;
        this.finished = finished;
    }
}
