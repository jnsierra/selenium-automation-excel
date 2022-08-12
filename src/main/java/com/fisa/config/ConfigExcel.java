package com.fisa.config;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ConfigExcel {

    @Value("${excel.file.mac}")
    private String fileMac;
    @Value("${excel.file.windows}")
    private String fileWindows;

    @Value("${picture.evidence.windows}")
    private String fileScreenShootWin;

    @Value("${picture.evidence.mac}")
    private String fileScreenShootMac;

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
            System.out.println("Excel a leer: " + file);
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

    @Bean("urlbasescreenshot")
    @Scope("singleton")
    @DependsOn({"getDriver"})
    public String getUrlBase(@Qualifier("IdTransaccional")Integer idTransaccional){
        String fileBase = "";
        String sSistemaOperativo = System.getProperty("os.name");
        if(sSistemaOperativo.contains("Mac")){
            fileBase = fileScreenShootMac;
        }else{
            fileBase = fileScreenShootWin;
        }
        //Creamos la nueva carpeta
        fileBase = fileBase + idTransaccional + "/";
        Path path = Paths.get(fileBase);
        if(!Files.exists(path)){
            try {
                Files.createDirectory(path);
                System.out.println("Se creo carpeta " + fileBase);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return fileBase;
    }

}
