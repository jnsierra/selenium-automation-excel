package com.fisa.service;

import com.fisa.dto.StepAutomationDTO;

public interface ManagePictures {

    void validatePhoto(StepAutomationDTO step, String time, Integer iterator);
}
