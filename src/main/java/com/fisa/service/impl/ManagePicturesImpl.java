package com.fisa.service.impl;

import com.fisa.dto.StepAutomationDTO;
import com.fisa.service.ManagePictures;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
@Service
public class ManagePicturesImpl implements ManagePictures {

    private WebDriver driver;
    private String urlFile;

    @Autowired
    public ManagePicturesImpl(WebDriver driver,@Qualifier("urlbasescreenshot") String urlBase) {
        this.driver = driver;
        this.urlFile = urlBase;
    }

    @Override
    public void validatePhoto(StepAutomationDTO step, String time, Integer iterator) {
        if("Y".equalsIgnoreCase(step.getTakePicture())){
            if( ("AFTER".equalsIgnoreCase(time) && time.equalsIgnoreCase(step.getTimePicture()) )
                    || ("BEFORE".equalsIgnoreCase(time) && time.equalsIgnoreCase(step.getTimePicture())) ){
                generatePhoto(step, iterator);
            }
        }
    }

    private void generatePhoto(StepAutomationDTO step, Integer iterator){
        TakesScreenshot scrShot =  ( (TakesScreenshot) driver );
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(generateName(step, iterator));
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateName(StepAutomationDTO step,Integer iterator){
        String file = "";
        file = urlFile + iterator + "_image-"+ step.getLabelAccion() + ".png";
        return file;
    }

}
