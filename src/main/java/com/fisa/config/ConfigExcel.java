package com.fisa.config;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class ConfigExcel {

    private String file = "/Users/sierraj/fisa/repositories/excel-automation/Book1.xlsx";

    @Bean("WorkbookBean")
    @Scope("singleton")
    public XSSFWorkbook getExcel(@Qualifier("excelFile") FileInputStream file){
        try {
            return new XSSFWorkbook(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean("excelFile")
    @Scope("singleton")
    public FileInputStream getInputStream(){
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
    @Bean("sheetExcel")
    @Scope("singleton")
    public XSSFSheet getSheetZero(@Qualifier("WorkbookBean") XSSFWorkbook workbook){
        return workbook.getSheetAt(0);
    }
}
