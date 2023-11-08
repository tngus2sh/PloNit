package com.plonit.ploggingservice.api.item.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder(access = PRIVATE)
public class FindTrashcanRes {

    private Double latitude;
    private Double longitude;

}
