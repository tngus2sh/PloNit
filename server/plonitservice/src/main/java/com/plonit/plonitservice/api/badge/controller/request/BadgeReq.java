package com.plonit.plonitservice.api.badge.controller.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BadgeReq {

    private String name;

    private MultipartFile image;

    private Boolean type;

    private String status;

    private Integer value;

    private String startDate;

    private String endDate;

}
