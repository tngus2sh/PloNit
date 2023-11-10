package com.plonit.plonitservice.domain.rank;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash
@Getter
@Setter
public class Ranking {
    
    @Id
    private String id;
    
    private String memberKey;
    
    private String count;
    
    private String distance;
    
    private String time;
    
    private String calorie;
    
    private String rank;
    
    
}
