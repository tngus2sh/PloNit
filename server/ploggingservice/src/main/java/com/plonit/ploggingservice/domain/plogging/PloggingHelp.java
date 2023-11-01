package com.plonit.ploggingservice.domain.plogging;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PloggingHelp extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plogging_help_id")
    private Long id;
    
    @Column
    private Long memberKey;
    
    @Column
    private Long gugunCode;
    
    @Column
    private Double latitude;
    
    @Column
    private Double longitude;
    
    @Column
    private String place;
    
    @Column
    private String image;
    
    @Column
    private String context;


    @Builder
    public PloggingHelp(Long memberKey, Long gugunCode, Double latitude, Double longitude, String place, String image, String context) {
        this.memberKey = memberKey;
        this.gugunCode = gugunCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.place = place;
        this.image = image;
        this.context = context;
    }
}
