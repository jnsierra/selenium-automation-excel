package com.fisa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StepTestTrackingDTO {

    private String label;
    private Long time;
    private Boolean state;

}
