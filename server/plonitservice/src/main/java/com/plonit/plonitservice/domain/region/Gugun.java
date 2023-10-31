package com.plonit.plonitservice.domain.region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@DynamicInsert
@DynamicUpdate
public class Gugun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gugun_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_id", nullable = false)
    private Sido sido;

    @Column(nullable = false, unique = true)
    private Long code;

    @Column(nullable = false)
    private String name;
}
