package com.plonit.ploggingservice.api.excel.service;

import com.plonit.ploggingservice.api.excel.service.dto.PloggingDto;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Transactional
public interface ExcelService {
    List<PloggingDto> findPloggins(Long ploggingId);
    void makeExcel(List<PloggingDto> data);
    void saveExcel(Workbook workbook);
}
