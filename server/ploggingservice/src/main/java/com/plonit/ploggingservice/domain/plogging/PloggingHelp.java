package com.plonit.ploggingservice.domain.plogging;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
    
}
