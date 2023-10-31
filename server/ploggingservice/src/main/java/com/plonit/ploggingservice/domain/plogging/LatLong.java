package com.plonit.ploggingservice.domain.plogging;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Table(name = "latlong", indexes = @Index(name = "idx_lat_long", columnList = "latitude, longitude", unique = true))
@RequiredArgsConstructor
public class LatLong extends TimeBaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lat_long_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "plogging_id", nullable = false)
    private Plogging plogging;
    
    @Column
    private Double latitude;
    
    @Column
    private Double longitude;

    @Builder
    public LatLong(Long id, Plogging plogging) {
        this.id = id;
        this.plogging = plogging;
    }

    public static LatLong toEntity(Plogging plogging, Double latitude, Double longitude) {
        return LatLong.builder()
                .plogging(plogging)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
    
}
