package com.fisa.service;

import com.fisa.dto.StepAutomationDTO;

public interface StepExecution {

    Boolean executeStep(StepAutomationDTO stepAutomationDTO, String principalChild, Integer iterator)throws InterruptedException;
}