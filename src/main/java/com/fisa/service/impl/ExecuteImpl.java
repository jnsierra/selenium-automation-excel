package com.fisa.service.impl;

import com.fisa.service.Execute;
import com.fisa.service.ManageExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

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
        System.out.println("Paso por aqui");
    }
}
