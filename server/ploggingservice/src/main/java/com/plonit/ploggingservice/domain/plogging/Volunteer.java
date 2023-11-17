package com.plonit.ploggingservice.domain.plogging;

import com.plonit.ploggingservice.common.enums.Finished;
import com.plonit.ploggingservice.common.enums.VolunteerStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Volunteer extends TimeBaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "volunteer_id")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "plogging_id", nullable = false)
    private Plogging plogging;
    
    @Column
    @Enumerated(EnumType.STRING)
    private VolunteerStatus status;

    @Builder
    public Volunteer(Long id, Plogging plogging, VolunteerStatus status) {
        this.id = id;
        this.plogging = plogging;
        this.status = status;
    }
}
