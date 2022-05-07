package com.fisa.service;

import com.fisa.dto.StepAutomationDTO;

import java.util.List;

public interface ManageExcel {

    List<StepAutomationDTO> getStepsExcel();

    String getUrlApp();

}
