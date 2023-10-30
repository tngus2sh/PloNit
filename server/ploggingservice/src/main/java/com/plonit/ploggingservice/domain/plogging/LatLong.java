package com.plonit.ploggingservice.domain.plogging;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class LatLong extends TimeBaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lat_long_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "plogging_id", nullable = false)
    private Plogging plogging;

    @Builder
    public LatLong(Long id, Plogging plogging) {
        this.id = id;
        this.plogging = plogging;
    }
    
}
