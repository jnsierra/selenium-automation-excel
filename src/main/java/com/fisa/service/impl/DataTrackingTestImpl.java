package com.fisa.service.impl;

import com.fisa.dto.StepTestTrackingDTO;
import com.fisa.dto.TestTrackingDTO;
import com.fisa.service.DataTrackingTest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Scope("singleton")
public class DataTrackingTestImpl implements DataTrackingTest {

    private TestTrackingDTO testTrackingDTO;

    public DataTrackingTestImpl() {
        this.testTrackingDTO = TestTrackingDTO.builder()
                .dateIni(new Date())
                .state(Boolean.FALSE)
                .build();
    }

    @Override
    public TestTrackingDTO getTracking() {
        return null;
    }

    @Override
    public void generateFin() {
        this.testTrackingDTO.setDateFin(new Date());
        this.testTrackingDTO.setTimeTest(this.testTrackingDTO.getDateFin().getTime() - this.testTrackingDTO.getDateIni().getTime());
    }

    @Override
    public Long getTimeTest() {
        return this.testTrackingDTO.getTimeTest();
    }

    @Override
    public void setStepTestTracking(StepTestTrackingDTO stepTestTrackingDTO) {
        testTrackingDTO.addStep(stepTestTrackingDTO);
    }

    @Override
    public List<StepTestTrackingDTO> getSteps() {
        return this.testTrackingDTO.getSteps();
    }
}