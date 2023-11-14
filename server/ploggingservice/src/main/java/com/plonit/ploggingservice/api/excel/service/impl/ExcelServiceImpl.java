package com.plonit.ploggingservice.api.excel.service.impl;

import com.plonit.ploggingservice.api.excel.controller.MemberFeignClient;
import com.plonit.ploggingservice.api.excel.controller.response.VolunteerMemberInfoRes;
import com.plonit.ploggingservice.api.excel.service.ExcelService;
import com.plonit.ploggingservice.api.excel.service.MailService;
import com.plonit.ploggingservice.api.excel.service.dto.ExcelColumn;
import com.plonit.ploggingservice.api.excel.service.dto.ExcelDto;
import com.plonit.ploggingservice.api.excel.service.dto.MailDto;
import com.plonit.ploggingservice.api.excel.service.dto.PloggingDto;
import com.plonit.ploggingservice.common.exception.CustomException;
import com.plonit.ploggingservice.domain.plogging.repository.PloggingQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.plonit.ploggingservice.common.exception.ErrorCode.INVALID_MAKING_EXCEL;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    @Value("${file.path.excel}")
    private String excelFilePath;

    private final MailService mailService;
    private final PloggingQueryRepository ploggingQueryRepository;
    private final MemberFeignClient memberFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;


    @Override
    public void findVolunteerPloggings() {
        LocalDate oneWeekBefore = LocalDate.now().minusWeeks(1);
        int day = oneWeekBefore.getDayOfWeek().getValue() - 1;

        LocalDateTime startDate = oneWeekBefore.minusDays(day).atStartOfDay();
        LocalDateTime endDate = oneWeekBefore.plusDays(6 - day).atTime(LocalTime.MAX);

        List<PloggingDto> ploggings = ploggingQueryRepository.findVolunteerPloggings(startDate, endDate);
//        List<PloggingDto> ploggings = ploggingQueryRepository.findVolunteerPloggings(null, null);

        List<Long> memberIdList = ploggings.stream().map(ploggingEntity -> {
            return ploggingEntity.getMemberId();
        }).collect(Collectors.toList());

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        List<VolunteerMemberInfoRes> volunteerInfos = circuitBreaker.run(
                () -> memberFeignClient.findVolunteerMemberInfo(memberIdList).getResultBody(),
                throwable -> null
        );

        List<ExcelDto> excelDtoList = new ArrayList<>();

        if(volunteerInfos != null) {
            for(int i = 0; i < ploggings.size(); i++) {
                if(volunteerInfos.get(i) != null) {
                    excelDtoList.add(ExcelDto.of(ploggings.get(i), volunteerInfos.get(i)));
                }
            }
        }
        else {
            throw new CustomException(INVALID_MAKING_EXCEL);
        }

        makeExcel(excelDtoList);
    }

    @Override
    public void makeExcel(List<ExcelDto> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("봉사 플로깅");

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Cell cell = null;
        Row row = null;

        List<String> excelHeaderList = getHeaderName(getClass(data));

        // Header
        row = sheet.createRow(0); // 행 생성
        for (int i = 0; i < excelHeaderList.size(); i++) {
            cell = row.createCell(i); // 열 생성
            cell.setCellStyle(headerStyle); // cell에 style 지정
            cell.setCellValue(excelHeaderList.get(i)); // 열에 컬럼명을 넣어줌
        }

        // Body
        int rowCount = 1; // 현재 행의 개수를 가지고 있는 변수 rowCount 선언 (Header를 그리고 시작했으므로 1부터 시작)

        for(ExcelDto excelDto : data) {
            row = sheet.createRow(rowCount++);

            cell = row.createCell(0);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(excelDto.getId1365());

            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(excelDto.getName());

            cell = row.createCell(2);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(excelDto.getBirth());

            cell = row.createCell(3);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(excelDto.getTime());

            cell = row.createCell(4);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(excelDto.getDistance());

            cell = row.createCell(5);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(excelDto.getStartImage());

            cell = row.createCell(6);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(excelDto.getMiddleImage());

            cell = row.createCell(7);
            cell.setCellStyle(dataStyle);
            cell.setCellValue(excelDto.getEndImage());
        }

        for(int i = 0; i < excelHeaderList.size(); i++){
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+1024); // 너비 더 넓게
        }

        saveExcel(workbook);
    }

    @Override
    public void saveExcel(Workbook workbook) {
        File folder = new File(excelFilePath);

        if(folder != null && !folder.exists()) {
            folder.mkdirs();
        }

        LocalDate oneWeekBefore = LocalDate.now().minusWeeks(1);

        int year = oneWeekBefore.getYear();
        int month = oneWeekBefore.getMonthValue();
        int count = oneWeekBefore.getDayOfMonth() / 7 + 1;

        StringBuffer sb = new StringBuffer();
        sb.append(year);
        sb.append("년_");
        sb.append(month);
        sb.append("월_");
        sb.append(count);
        sb.append("주차_봉사플로깅.xlsx");
        String saveFileName = sb.toString();

        String fileLocation = excelFilePath + "\\" + saveFileName;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileLocation);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        MailDto mailDto = MailDto.builder()
                .to("ljh8190@naver.com")
                .title("봉사 플로깅 입니다.")
                .content("내용")
                .filename(fileLocation)
                .build();

//        mailService.sendMail();
    }

    // 엑셀 헤더명을 반환해 주는 메소드
    private List<String> getHeaderName(Class<?> type) {

        // 매개변수로 받은 클래스의 필드를 배열로 받아 스트림 생성 -> @ExcelColumn 어노테이션 붙은 필드만 수집해 헤더로 설정한 값을 추출
        List<String> excelHeaderNameList =  Arrays.stream(type.getDeclaredFields())
                .filter(s -> s.isAnnotationPresent(ExcelColumn.class))
                .map(s -> s.getAnnotation(ExcelColumn.class).headerName())
                .collect(Collectors.toCollection(LinkedList::new));

        // List가 비어 있을 경우 헤더 이름이 지정되지 않은 것이므로 예외 발생
        if(CollectionUtils.isEmpty(excelHeaderNameList)) {
            log.error("헤더 이름이 조회되지 않아 예외 발생!");
            throw new IllegalStateException("헤더 이름이 없습니다.");
        }

        return excelHeaderNameList;
    }

    // List(데이터 리스트)에 담긴 dto의 클래스 정보를 반환하는 메서드
    private Class<?> getClass(List<?> data) {
        // 모든 dto는 같은 필드를 가지고 있으므로 맨 마지막 DTO만 빼서 클래스 정보 반환
        if(!CollectionUtils.isEmpty(data)) {
            return data.get(data.size()-1).getClass();
        } else {
            log.error("리스트가 비어 있어서 예외 발생!");
            throw new IllegalStateException("조회된 리스트가 비어 있습니다. 확인 후 다시 진행해주시기 바랍니다.");
        }
    }
}
