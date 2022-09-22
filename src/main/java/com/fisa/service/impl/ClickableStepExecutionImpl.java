package com.fisa.service.impl;

import com.fisa.dto.StepAutomationDTO;
import com.fisa.dto.StepTestTrackingDTO;
import com.fisa.service.ManagePictures;
import com.fisa.service.ManageWaits;
import com.fisa.service.SaveInformation;
import com.fisa.service.StepExecution;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Service
public class ClickableStepExecutionImpl implements StepExecution {

    private ManageWaits manageWaits;

    private SaveInformation saveInformation;

    private WebDriver driver;

    private String principalChild;

    private ManagePictures managePictures;

    private static final Logger logger = Logger.getLogger(ClickableStepExecutionImpl.class);

    @Autowired
    public ClickableStepExecutionImpl(ManageWaits manageWaits, WebDriver driver
            , SaveInformation saveInformation, ManagePictures managePictures
            ) {
        this.manageWaits = manageWaits;
        this.driver = driver;
        this.saveInformation = saveInformation;
        this.managePictures = managePictures;
    }

    @Override
    public Boolean executeStep(StepAutomationDTO step, String principalChild, Integer iterator) throws InterruptedException {
        this.principalChild = principalChild;

        this.manageWindow(step);
        this.managePictures.validatePhoto(step,"BEFORE", iterator);
        Optional<WebElement> element = manageWaits.waitAndReturnElement(step);
        if (element.isPresent()) {
            Boolean response = executeStepSingle(step, element.get());
            if(!response){
                return Boolean.FALSE;
            }
            addAdditionalKey(step, element.get());
        } else {
            return Boolean.FALSE;
        }
        Thread.sleep(step.getSleepAfter()*1000);


        this.managePictures.validatePhoto(step,"AFTER", iterator);

        return Boolean.TRUE;
    }

    public Boolean executeStepSingle(StepAutomationDTO step, WebElement element) {
        try {
            if ("N/A".equalsIgnoreCase(step.getInput())) {
                if ("Y".equalsIgnoreCase(step.getExtractInformation())) {
                    //Metodo con el cual se maneja la informacion qu se extrae de la aplicacion
                    manageExtractInformation(step, element);
                } else {
                    clicElement(step, element);
                }
            } else if (!"N/A".equalsIgnoreCase(step.getInput())) {
                //valida si debe ingresar informacion
                if("N".equalsIgnoreCase(step.getInputInfoForVariable())){
                    element.sendKeys(step.getInput());
                }else{
                    element.sendKeys(saveInformation.getValue(step.getInput()));
                }
            }
        } catch (Exception e) {
            logger.error("Error en el elemento (".concat(step.getLabelAccion()).concat(")") + " En la linea de ejecución: ("+step.getIterator()+") al ejecutar la accion deseada. " );
            logger.error("Con el elemento identificador (".concat(step.getFindBy()).concat(")"));
            logger.error(e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean manageWindow(StepAutomationDTO step) {
        if ("principal".equalsIgnoreCase(step.getWindow())) {
            driver.switchTo().window(this.principalChild);
        } else {
            Set<String> s1 = driver.getWindowHandles();
            Iterator<String> i1 = s1.iterator();
            while (i1.hasNext()) {
                String childWindow = i1.next();
                if (!this.principalChild.equalsIgnoreCase(childWindow)) {
                    driver.switchTo().window(childWindow);
                }
            }

        }
        return Boolean.TRUE;
    }

    public void addAdditionalKey(StepAutomationDTO step, WebElement element) {
        if (!"N/A".equalsIgnoreCase(step.getAdditionalInput())) {
            logger.info("Se generara el input de un key adicional en el label:".concat(step.getLabelAccion()).concat(" con el key ").concat(step.getAdditionalInput()));
            if ("TAB".equalsIgnoreCase(step.getAdditionalInput())) {
                element.sendKeys(Keys.TAB);
            }
        }
    }

    public void manageExtractInformation(StepAutomationDTO step, WebElement element){
        String valor = element.getAttribute("value");
        logger.info("Valor con label:".concat(step.getLabelAccion()).concat(" ; ").concat(valor));
        if("Y".equalsIgnoreCase(step.getSaveInformation())){
            //Almacenamos la informacion
            this.saveInformation.guardaInformacion(valor, step.getVariable());
        }
    }

    public void clicElement(StepAutomationDTO step, WebElement element){
        try {
            if("S".equals(step.getScroll())){
                logger.debug("Se ejecutara el scroll para el elemento");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView();", element);
            }
            element.click();
        }catch (ElementClickInterceptedException e){
            logger.error("Error en el elemento (".concat(step.getLabelAccion()).concat(")") + " En la linea de ejecución: ("+step.getIterator()+") Genera excepcion al momento de dar clic. " );
            logger.error("Con el elemento identificador (".concat(step.getFindBy()).concat(")"));
            logger.error(e);
            clickElemntWithJs(step, element);
        }

    }

    public void clickElemntWithJs(StepAutomationDTO step, WebElement element){
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.error("Se iniciara a dar click con JavaScript al fallar por medio de selenium");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

}
