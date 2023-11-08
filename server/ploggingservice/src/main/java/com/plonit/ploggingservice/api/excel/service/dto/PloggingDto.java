package com.plonit.ploggingservice.api.excel.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder()
public class PloggingDto {

    @ExcelColumn(headerName = "1365 아이디")
    private String id1365;

    @ExcelColumn(headerName = "이름")
    private String name;

    @ExcelColumn(headerName = "생년월일")
    private String birth;

    @ExcelColumn(headerName = "플로깅 시간")
    private Long time;

    @ExcelColumn(headerName = "플로깅 거리")
    private Double distance;

    @ExcelColumn(headerName = "플로깅 시작 사진")
    private String startImage;

    @ExcelColumn(headerName = "플로깅 중간 사진")
    private String middleImage;

    @ExcelColumn(headerName = "플로깅 종료 사진")
    private String endImage;
}
