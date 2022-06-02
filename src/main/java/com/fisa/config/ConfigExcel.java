package com.fisa.config;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
@PropertySource("file:/opt/repository/configuration/application.properties")
public class ConfigExcel {

    @Value("${excel.file.mac}")
    private String fileMac;
    @Value("${excel.file.windows}")
    private String fileWindows;

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
            String file="";
            String sSistemaOperativo = System.getProperty("os.name");
            System.out.println(sSistemaOperativo);
            if(sSistemaOperativo.contains("Mac")){
                file = fileMac;
            }else{
                file = fileWindows;
            }
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
