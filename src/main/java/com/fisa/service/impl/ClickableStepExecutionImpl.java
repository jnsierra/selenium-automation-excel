package com.fisa.service.impl;

import com.fisa.dto.StepAutomationDTO;
import com.fisa.service.ManageWaits;
import com.fisa.service.StepExecution;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Service
public class ClickableStepExecutionImpl implements StepExecution {

    private ManageWaits manageWaits;

    private WebDriver driver;

    private String principalChild;

    private static final Logger logger = Logger.getLogger(ClickableStepExecutionImpl.class);

    @Autowired
    public ClickableStepExecutionImpl(ManageWaits manageWaits,WebDriver driver) {
        this.manageWaits = manageWaits;
        this.driver = driver;
    }

    @Override
    public Boolean executeStep(StepAutomationDTO step,String principalChild) {
        this.principalChild = principalChild;
        try {
            this.manageWindow(step);
            Optional<WebElement> element = manageWaits.waitAndReturnElement(step);
            if(element.isPresent()){
                executeStepSingle(step, element.get());
                addAdditionalKey(step,element.get());
            }
        } catch (InterruptedException e) {
            if("Y".equalsIgnoreCase(step.getRequiered())){
                throw new RuntimeException(e);
            }else{
                logger.error(e.getMessage());
            }
        }
        logger.info("Se ejecuto la acción con el label: ".concat(step.getLabelAccion()));
        return Boolean.TRUE;
    }

    public Boolean executeStepSingle(StepAutomationDTO step, WebElement element){
            if("N/A".equalsIgnoreCase(step.getInput())){
                if("Y".equalsIgnoreCase(step.getExtractInformation())){
                    String valor = element.getAttribute("value");
                    logger.info("Valor con label:".concat(step.getLabelAccion()).concat(" ; ").concat(valor));
                }else{
                    element.click();
                }
            }else if(!"N/A".equalsIgnoreCase(step.getInput())){
                element.sendKeys(step.getInput());
            }
        return Boolean.TRUE;
    }
    public Boolean manageWindow(StepAutomationDTO step){
        if("principal".equalsIgnoreCase(step.getWindow())){
            driver.switchTo().window(this.principalChild);
        }else{
            Set<String> s1= driver.getWindowHandles();
            Iterator<String> i1=s1.iterator();
            while(i1.hasNext()){
                String childWindow = i1.next();
                if(!this.principalChild.equalsIgnoreCase(childWindow)){
                    driver.switchTo().window(childWindow);
                }
            }

        }
        return Boolean.TRUE;
    }

    public void addAdditionalKey(StepAutomationDTO step, WebElement element){
        if(!"N/A".equalsIgnoreCase(step.getAdditionalInput())){
            logger.info("Se generara el input de un key adicional en el label:".concat(step.getLabelAccion()).concat(" con el key ").concat(step.getAdditionalInput()));
            if("TAB".equalsIgnoreCase(step.getAdditionalInput())){
                element.sendKeys(Keys.TAB);
            }
        }
    }

}
