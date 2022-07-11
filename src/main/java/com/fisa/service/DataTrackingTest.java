package com.fisa.service;

import com.fisa.dto.StepTestTrackingDTO;
import com.fisa.dto.TestTrackingDTO;

import java.util.List;

public interface DataTrackingTest {

    TestTrackingDTO getTracking();

    void generateFin();

    Long getTimeTest();

    void setStepTestTracking(StepTestTrackingDTO stepTestTrackingDTO);

    List<StepTestTrackingDTO> getSteps();
}
