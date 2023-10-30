package com.plonit.ploggingservice.domain.plogging;


import com.plonit.ploggingservice.common.enums.Finished;
import com.plonit.ploggingservice.common.enums.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
public class Plogging extends TimeBaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plogging_id")
    private Long id;
    
    @Column(name = "member_key")
    private String memberKey;
    
    @Column
    @Enumerated(EnumType.STRING)
    private Type type;
    
    @Column
    private String place;
    
    @Column
    private LocalDateTime startTime;
    
    @Column
    private LocalDateTime endTime;
    
    @Column
    private double totalTime;
    
    @Column
    private double distance;
    
    @Column
    private double calorie;
    
    @Column
    private String review;
    
    @Column
    @Enumerated(EnumType.STRING)
    private Finished finished;
    
    @Column
    private LocalDate date;

    @Builder
    public Plogging(String memberKey, Type type, String place, LocalDateTime startTime, LocalDateTime endTime, double totalTime, double distance, double calorie, String review, Finished finished, LocalDate date) {
        this.memberKey = memberKey;
        this.type = type;
        this.place = place;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.distance = distance;
        this.calorie = calorie;
        this.review = review;
        this.finished = finished;
        this.date = date;
    }

    public static Plogging toFirstEntity(String memberKey, Type type, String place, LocalDateTime startTime, Finished finished, LocalDate date) {
        return Plogging.builder()
                .memberKey(memberKey)
                .type(type)
                .place(place)
                .startTime(startTime)
                .finished(finished)
                .date(date)
                .build();
    }
    
    
}
