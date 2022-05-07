package com.fisa.dto;

import com.fisa.enumeration.TypeFindByEnum;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StepAutomationDTO {

    private String window;
    private TypeFindByEnum typeFindByEnum;
    private String findBy;

}
