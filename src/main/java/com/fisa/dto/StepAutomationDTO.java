package com.fisa.dto;

import com.fisa.enumeration.TypeAdditionalWait;
import com.fisa.enumeration.TypeFindByEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder @Data
public class StepAutomationDTO {

    private String window;
    private TypeFindByEnum typeFindByEnum;
    private String findBy;
    private Long timeOutWait;
    private Long repeatTime;
    private TypeAdditionalWait additionalTypeWait;
    private Long timeAdditional;

}
