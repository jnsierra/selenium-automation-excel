package com.fisa.dto;

import com.fisa.enumeration.TypeAdditionalWait;
import com.fisa.enumeration.TypeFindByEnum;
import lombok.Builder;
import lombok.Data;

@Builder @Data
public class StepAutomationDTO {

    private String window;
    private TypeFindByEnum typeFindByEnum;
    private String findBy;
    private Long timeOutWait;
    private Long repeatTime;
    private TypeAdditionalWait additionalTypeWait;
    private Long timeAdditional;
    private String input;
    private String labelAccion;
    private Long sleepBefore;
    private Long sleepAfter;
    private String additionalInput;
    private String requiered;
    private String extractInformation;
    private String saveInformation;
    private String variable;
    private String inputInfoForVariable;
    private String takePicture;
    private String timePicture;
    private Integer iterator;
    private String retry;
    private Long stepRetry;
    private Long numberOfRetries;
    private Long currentNumbersOfRetries;
}
