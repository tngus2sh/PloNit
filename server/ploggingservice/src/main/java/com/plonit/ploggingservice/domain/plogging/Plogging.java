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
    private Long memberKey;
    
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
    private Long totalTime; // 초 단위
    
    @Column
    private Double distance;
    
    @Column
    private Double calorie;
    
    @Column
    private String review;
    
    @Column
    @Enumerated(EnumType.STRING)
    private Finished finished;
    
    @Column
    private LocalDate date;

    @Builder
    public Plogging(Long memberKey, Type type, String place, LocalDateTime startTime, LocalDateTime endTime, Long totalTime, Double distance, Double calorie, String review, Finished finished, LocalDate date) {
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

    public static Plogging toFirstEntity(Long memberKey, Type type, String place, LocalDateTime startTime, Finished finished, LocalDate date) {
        return Plogging.builder()
                .memberKey(memberKey)
                .type(type)
                .place(place)
                .startTime(startTime)
                .finished(finished)
                .date(date)
                .build();
    }

    public void saveEndPlogging(LocalDateTime endTime, Long totalTime, Double distance, Double calorie, String review, Finished finished) {
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.distance = distance;
        this.calorie = calorie;
        this.review = review;
        this.finished = finished;
    }
    
}
