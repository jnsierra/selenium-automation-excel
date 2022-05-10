package com.fisa.service.impl;

import com.fisa.dto.StepAutomationDTO;
import com.fisa.service.AutomationExecute;
import com.fisa.service.ManageExcel;
import com.fisa.service.StepExecution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutomationExecuteImpl implements AutomationExecute {

    private static final Logger logger = LogManager.getLogger(ExecuteImpl.class);

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
        this.initializeBrowser();
        //Obtengo la ventana de principal
        this.principalChild = driver.getWindowHandle();
        automation.forEach(step -> {
            stepExecution.executeStep(step, this.principalChild);
        });
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
}
