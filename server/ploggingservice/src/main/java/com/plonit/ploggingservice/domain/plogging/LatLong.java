package com.plonit.ploggingservice.domain.plogging;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "latlong", indexes = @Index(name = "idx_lat_long", columnList = "latitude, longitude", unique = true))
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    public LatLong(Plogging plogging, Double latitude, Double longitude) {
        this.plogging = plogging;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static LatLong toEntity(Plogging plogging, Double latitude, Double longitude) {
        return LatLong.builder()
                .plogging(plogging)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
    
}
