package com.fisa.service.impl;

import com.fisa.service.AutomationExecute;
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

    private WebDriver driver;

    private ManagePictures managePictures;
    @Autowired
    public ExecuteImpl(AutomationExecute automationExecute,WebDriver driver, ManagePictures managePictures) {
        this.automationExecute = automationExecute;
        this.driver = driver;
        this.managePictures = managePictures;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void executeTest() {
        long startTime = System.currentTimeMillis();
        logger.info("Se inicia la automatizacion:");
        try {
            this.automationExecute.executeAutomation();
        } catch (InterruptedException e) {
            logger.error("Se cierra el browser al generar un error");
            logger.error(e);
            this.driver.quit();
            throw new RuntimeException(e);
        }finally {
            long endTime = System.currentTimeMillis() - startTime;
            logger.info("Se finalizo la prueba en ".concat("" + (endTime/1000)).concat(" segundos") );
        }
    }
}
