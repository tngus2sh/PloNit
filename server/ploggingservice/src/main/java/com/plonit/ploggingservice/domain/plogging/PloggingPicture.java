package com.plonit.ploggingservice.domain.plogging;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class PloggingPicture extends TimeBaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plogging_picture_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "plogging_id", nullable = false)
    private Plogging plogging;
    
    @Column
    private String image;

    @Builder
    public PloggingPicture(Long id, Plogging plogging, String image) {
        this.id = id;
        this.plogging = plogging;
        this.image = image;
    }
}
