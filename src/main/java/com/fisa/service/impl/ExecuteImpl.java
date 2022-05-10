package com.fisa.service.impl;

import com.fisa.service.AutomationExecute;
import com.fisa.service.Execute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ExecuteImpl implements Execute {
    private static final Logger logger = LogManager.getLogger(ExecuteImpl.class);
    private AutomationExecute automationExecute;

    @Autowired
    public ExecuteImpl(AutomationExecute automationExecute) {
        this.automationExecute = automationExecute;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void executeTest() {
        logger.info("Se inicia la automatizacion:");
        try {
            this.automationExecute.executeAutomation();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
