package com.plonit.plonitservice.api.member.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRankRes {
    
    private Long memberId;
    
    private String nickName;
    
    private String profileImage;
    
}
