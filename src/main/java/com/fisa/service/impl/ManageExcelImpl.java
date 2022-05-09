package com.fisa.service.impl;

import com.fisa.dto.StepAutomationDTO;
import com.fisa.enumeration.TypeAdditionalWait;
import com.fisa.enumeration.TypeFindByEnum;
import com.fisa.service.ManageExcel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoublePredicate;

@Service
public class ManageExcelImpl implements ManageExcel {

    private XSSFSheet sheet;

    @Autowired
    public ManageExcelImpl(XSSFSheet sheet) {
        this.sheet = sheet;
    }

    @Override
    public List<StepAutomationDTO> getStepsExcel() {
        List<StepAutomationDTO> listOfAutomation = null;
        for(int i = 2; i < sheet.getPhysicalNumberOfRows(); i++){
            if(i==2){
                listOfAutomation = new ArrayList<>();
            }
            StepAutomationDTO step = StepAutomationDTO.builder()
                    .window(getStringValueCell(i, 0))
                    .typeFindByEnum(TypeFindByEnum.DEFAULT.getEnum(getStringValueCell(i, 1)))
                    .findBy(getStringValueCell(i, 2))
                    .timeOutWait(getLongValueCell(i,3))
                    .repeatTime(getLongValueCell(i,4))
                    .additionalTypeWait(TypeAdditionalWait.DEFAULT.getEnum(getStringValueCell(i,5)))
                    .timeAdditional(getLongValueCell(i,6))
                    .build();
            listOfAutomation.add(step);
            System.out.println("Test: " + step);
        }
        return listOfAutomation;
    }

    @Override
    public String getUrlApp() {
        //Leemos la primera linea del excel
        return getStringValueCell(0, 1);
    }

    private String getStringValueCell(int row, int column){
        XSSFRow rowx = sheet.getRow(row);
        XSSFCell url = rowx.getCell(column);
        return url.getStringCellValue();
    }

    private Long getLongValueCell(int row, int column){
        XSSFRow rowx = sheet.getRow(row);
        XSSFCell url = rowx.getCell(column);
        double valor = url.getNumericCellValue();
        return (long) valor;
    }

}
