package com.fisa.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Builder
public class TestTrackingDTO {

    private Date dateIni;
    private Date dateFin;
    private Long timeTest;
    private Boolean state;
    private List<StepTestTrackingDTO> steps;
    public void addStep(StepTestTrackingDTO step){
        if(Objects.isNull(steps)){
            this.steps = new ArrayList<>();
        }
        Boolean encontrado = Boolean.FALSE;
        //Valido si existe el item
        for(StepTestTrackingDTO item : steps ){
            if( Objects.nonNull(item.getLabel())  && item.getLabel().equalsIgnoreCase(step.getLabel())){
                //Quiere decir que es un reintento
                item.setTime( item.getTime() + step.getTime() );
                encontrado = Boolean.TRUE;
            }
        }
        if(!encontrado){
            steps.add(step);
        }
    }
}
