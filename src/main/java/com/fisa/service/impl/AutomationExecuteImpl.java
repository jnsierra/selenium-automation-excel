package com.fisa.service.impl;

import com.fisa.dto.StepAutomationDTO;
import com.fisa.service.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutomationExecuteImpl implements AutomationExecute {

    private static final Logger logger = Logger.getLogger(AutomationExecuteImpl.class);

    private ManageExcel manageExcel;

    private StepExecution stepExecution;

    private WebDriver driver;
    private String url;

    private String principalChild;
    private ManagePictures managePictures;
    private DataTrackingTest dataTrackingTest;
    private Boolean finishTest;

    @Autowired
    public AutomationExecuteImpl(ManageExcel manageExcel, WebDriver driver
            ,StepExecution stepExecution, ManagePictures managePictures
            ,DataTrackingTest dataTrackingTest) {
        this.manageExcel = manageExcel;
        this.url = this.manageExcel.getUrlApp();
        this.driver = driver;
        this.stepExecution = stepExecution;
        this.managePictures = managePictures;
        this.finishTest = Boolean.FALSE;
    }

    @Override
    public Boolean executeAutomation() throws InterruptedException {
        List<StepAutomationDTO> automation = this.manageExcel.getStepsExcel();
        validarInfoExcel(automation);
        this.initializeBrowser();
        //Obtengo la ventana de principal
        this.principalChild = driver.getWindowHandle();
        int i = 0;
        for(StepAutomationDTO item : automation){
            item.setIterator(i + 1);
            Boolean response = stepExecution.executeStep(item, this.principalChild, i +1);
            if(!response){
                this.finishTest = Boolean.FALSE;
                logger.error("Se finaliza la prueba por excepcion");
                this.managePictures.generateBugScreenShot();
                throw new InterruptedException("Se genero una excepcion en la ejecución de la prueba");
            }
            i++;
        }
        this.finishTest = Boolean.TRUE;
        logger.debug("Se ejecutaron ".concat(""+i).concat(" registros exitosamente. "));
        Thread.sleep(1000);
        this.driver.quit();
        return Boolean.TRUE;
    }

    public Boolean initializeBrowser(){
        driver.get(url);
        logger.debug("Abrimos la URL: ".concat(url));
        driver.manage().window().maximize();
        return Boolean.TRUE;
    }

    public void validarInfoExcel(List<StepAutomationDTO> automation){
        logger.debug("Ini Informacion cargada desde excel");
        automation.forEach(step -> logger.debug(step.toString()));
        logger.debug("Fin Informacion cargada: Con ".concat(""+automation.size()).concat(" registros. "));
    }

    public Boolean getFinishTest() {
        return finishTest;
    }
}
