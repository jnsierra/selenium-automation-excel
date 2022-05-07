package com.fisa.service.impl;

import com.fisa.dto.StepAutomationDTO;
import com.fisa.service.ManageExcel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageExcelImpl implements ManageExcel {

    @Override
    public List<StepAutomationDTO> getStepsExcel() {
        return null;
    }

    @Override
    public String getUrlApp() {
        return null;
    }
}
