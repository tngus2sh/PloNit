package com.plonit.ploggingservice.domain.plogging;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class ItemInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemInfo_id")
    private Long id;
    
    @Column
    private Long gugunkey;
    
    @Column
    private boolean contentType; // 0 : 화장실, 1 : 쓰레기통
    
    @Column
    private double latitude;
    
    @Column
    private double longitude;

    @Builder
    public ItemInfo(Long id, Long gugunkey, boolean contentType, double latitude, double longitude) {
        this.id = id;
        this.gugunkey = gugunkey;
        this.contentType = contentType;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
