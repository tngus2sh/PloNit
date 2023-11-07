package com.plonit.ploggingservice.api.excel.service.impl;

import com.plonit.ploggingservice.api.excel.service.ExcelService;
import com.plonit.ploggingservice.api.excel.service.dto.ExcelColumn;
import com.plonit.ploggingservice.api.excel.service.dto.PloggingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelServiceImpl implements ExcelService {

    @Override
    public List<PloggingDto> findPloggins(Long ploggingId) {
        return null;
    }

    @Override
    public Workbook makeExcel(List<PloggingDto> data) throws IOException {
        System.out.println("들어온 dto!!: " + data.get(0));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("봉사 플로깅");

        System.out.println("excel 생성됨!");

        Cell cell = null;
        Row row = null;

        List<String> excelHeaderList = getHeaderName(getClass(data));

        // Header
        row = sheet.createRow(0); // 행 생성
        for (int i = 0; i < excelHeaderList.size(); i++) {
            cell = row.createCell(i); // 열 생성
            cell.setCellValue(excelHeaderList.get(i)); // 열에 컬럼명을 넣어줌
        }

        // Body
        int rowCount = 1; // 현재 행의 개수를 가지고 있는 변수 rowCount 선언 (Header를 그리고 시작했으므로 1부터 시작)

        for(PloggingDto plogging : data) {
            row = sheet.createRow(rowCount++);

            cell = row.createCell(0);
            cell.setCellValue(plogging.getId1365());

            cell = row.createCell(1);
            cell.setCellValue(plogging.getName());

            cell = row.createCell(2);
            cell.setCellValue(plogging.getBirth());

            cell = row.createCell(3);
            cell.setCellValue(plogging.getTime());

            cell = row.createCell(4);
            cell.setCellValue(plogging.getDistance());

            cell = row.createCell(5);
            cell.setCellValue(plogging.getStartImage());

            cell = row.createCell(6);
            cell.setCellValue(plogging.getMiddleImage());

            cell = row.createCell(7);
            cell.setCellValue(plogging.getEndImage());
        }

//        File dir = new File("./ploggingservice/src/main/resources");
//        String path = dir.getAbsolutePath();
//        String path = new File("C:\\Users\\SSAFY\\Downloads").getAbsolutePath();
        String path = "C:\\Users\\SSAFY\\Downloads\\";
        String fileLocation = path.substring(0, path.length() - 1) + "test.xlsx";

        FileOutputStream fileOutputStream = new FileOutputStream(fileLocation);
        workbook.write(fileOutputStream);
        workbook.close();

        return workbook;
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
