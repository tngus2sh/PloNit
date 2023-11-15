package com.plonit.plonitservice.api.crew.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewRankRes {
    
    private Long id;
    
    private String crewImage;
    
    private String name;
    
}
