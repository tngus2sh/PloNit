package com.plonit.ploggingservice.domain.plogging;

import lombok.*;
import org.springframework.retry.annotation.CircuitBreaker;

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

    @Column
    private Boolean isActive; // 1: 도움 요청 활성화, 0: 도움 요청 비활성화

    public void updateIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
