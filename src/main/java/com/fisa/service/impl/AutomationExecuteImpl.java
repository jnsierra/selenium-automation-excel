package com.fisa.service.impl;

import com.fisa.dto.StepAutomationDTO;
import com.fisa.service.AutomationExecute;
import com.fisa.service.ManageExcel;
import com.fisa.service.StepExecution;
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

    @Autowired
    public AutomationExecuteImpl(ManageExcel manageExcel, WebDriver driver,StepExecution stepExecution) {
        this.manageExcel = manageExcel;
        this.url = this.manageExcel.getUrlApp();
        this.driver = driver;
        this.stepExecution = stepExecution;
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
            Boolean response = stepExecution.executeStep(item, this.principalChild, i +1);
            if(!response){
                logger.error("Se finaliza la prueba por excepcion");
                break ;
            }
            i++;
        }
        logger.info("Se ejecutaron ".concat(""+i).concat(" registros exitosamente. "));
        Thread.sleep(10000);
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
        logger.info("Ini Informacion cargada desde excel");
        automation.forEach(step -> logger.info(step.toString()));
        logger.info("Fin Informacion cargada: Con ".concat(""+automation.size()).concat(" registros. "));
    }
}
