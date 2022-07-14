package com.fisa.service.impl;

import com.fisa.dto.StepTestTrackingDTO;
import com.fisa.service.AutomationExecute;
import com.fisa.service.DataTrackingTest;
import com.fisa.service.Execute;
import com.fisa.service.ManagePictures;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ExecuteImpl implements Execute {
    private static final Logger logger = Logger.getLogger(ExecuteImpl.class);
    private AutomationExecute automationExecute;
    private DataTrackingTest dataTrackingTest;
    private WebDriver driver;

    @Autowired
    public ExecuteImpl(AutomationExecute automationExecute, WebDriver driver, ManagePictures managePictures,
                       DataTrackingTest dataTrackingTest) {
        this.automationExecute = automationExecute;
        this.driver = driver;
        this.dataTrackingTest = dataTrackingTest;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void executeTest() {
        long startTime = System.currentTimeMillis();
        logger.debug("Se inicia la automatizacion:");
        try {
            this.automationExecute.executeAutomation();
        } catch (InterruptedException e) {
            logger.error("Se cierra el browser al generar un error");
            logger.error(e);
            this.driver.quit();
            throw new RuntimeException(e);
        }finally {
            long endTime = System.currentTimeMillis() - startTime;
            logger.debug("Se finalizo la prueba en ".concat("" + (endTime/1000)).concat(" segundos") );
            this.dataTrackingTest.generateFin();
            this.writeTrackingLog();
        }
    }

    public void writeTrackingLog(){
        logger.info("[TRACKING_TEST][INI_TRACKING]|TEST_STATUS|"+this.automationExecute.getFinishTest()+"|");
        for(StepTestTrackingDTO item : this.dataTrackingTest.getSteps()){
            logger.info("[TRACKING_TEST][STEP_TRACKING]|"+item.getLabel()+"|" + item.getTime() + "|" + item.getState()+"|");
        }
        logger.info("[TRACKING_TEST][FINISH_TRACKING]|"+dataTrackingTest.getTimeTest()+"|");
    }
}