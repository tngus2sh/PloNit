package com.plonit.ploggingservice.domain.item;

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
    @Column(name = "item_info_id")
    private Long id;
    
    @Column
    private Long gugunCode;
    
    @Column
    private Boolean contentType; // 0 : 화장실, 1 : 쓰레기통
    
    @Column
    private Double latitude;
    
    @Column
    private Double longitude;

    @Builder
    public ItemInfo(Long gugunCode, Boolean contentType, Double latitude, Double longitude) {
        this.gugunCode = gugunCode;
        this.contentType = contentType;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
