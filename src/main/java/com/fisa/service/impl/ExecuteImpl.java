package com.fisa.service.impl;

import com.fisa.dto.StepAutomationDTO;
import com.fisa.service.Execute;
import com.fisa.service.ManageExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecuteImpl implements Execute {

    private ManageExcel manageExcel;

    @Autowired
    public ExecuteImpl(ManageExcel manageExcel) {
        this.manageExcel = manageExcel;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void executeTest() {
        String url = this.manageExcel.getUrlApp();
        List<StepAutomationDTO> automation = this.manageExcel.getStepsExcel();
        System.out.println("Paso por aqui: " + url);
    }
}
