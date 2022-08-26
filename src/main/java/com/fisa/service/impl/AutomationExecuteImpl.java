package com.fisa.service.impl;

import com.fisa.dto.StepAutomationDTO;
import com.fisa.dto.StepTestTrackingDTO;
import com.fisa.service.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutomationExecuteImpl implements AutomationExecute {

    private static final Logger logger = Logger.getLogger(AutomationExecuteImpl.class);

    private ManageExcel manageExcel;

    private StepExecution stepExecution;

    private WebDriver driver;
    private String url;
    private String imageLocation;
    private String principalChild;
    private ManagePictures managePictures;

    private DataTrackingTest dataTrackingTest;
    private Boolean finishTest;
    private ManageImageSelect manageImageSelect;

    @Autowired
    public AutomationExecuteImpl(ManageExcel manageExcel, WebDriver driver
            ,StepExecution stepExecution, ManagePictures managePictures
            ,DataTrackingTest dataTrackingTest
            ,ManageImageSelect manageImageSelect) {
        this.manageExcel = manageExcel;
        this.url = this.manageExcel.getUrlApp();
        this.imageLocation = this.manageExcel.getLocationResources();
        System.out.println("Ubicacion imagenes: " + this.imageLocation);
        this.driver = driver;
        this.stepExecution = stepExecution;
        this.managePictures = managePictures;
        this.finishTest = Boolean.FALSE;
        this.dataTrackingTest = dataTrackingTest;
        this.manageImageSelect = manageImageSelect;
    }

    @Override
    public Boolean executeAutomation() throws InterruptedException {
        List<StepAutomationDTO> automation = this.manageExcel.getStepsExcel();
        validarInfoExcel(automation);
        this.initializeBrowser();
        //Obtengo la ventana de principal
        this.principalChild = driver.getWindowHandle();
        int i = 0;
        for( i = 0 ; i< automation.size(); i++){
            //Inicio a tomar el tiempo
            long startTime = System.currentTimeMillis();
            StepAutomationDTO item = automation.get(i);
            item.setIterator(i + 1);
            Boolean response = Boolean.FALSE;
            if("N/A".equalsIgnoreCase(item.getImageName())){
                 response = stepExecution.executeStep(item, this.principalChild, i +1);
            }else{
                response = this.manageImageSelect.validateImageSelect(item);
            }
            i = manageRetries(automation, i, item, response);
            long endTime = System.currentTimeMillis() - startTime;
            logger.debug("Se ejecuto la acción en ".concat("" + (endTime / 1000)).concat(" segundos con el label: ").concat(item.getLabelAccion()));
            this.dataTrackingTest.setStepTestTracking(StepTestTrackingDTO.builder()
                    .label(item.getLabelAccion())
                    .time(endTime)
                    .state(Boolean.TRUE)
                    .build());
        }
        this.finishTest = Boolean.TRUE;
        logger.debug("Se ejecutaron ".concat(""+i).concat(" registros exitosamente. "));
        Thread.sleep(1000);
        this.driver.quit();
        return Boolean.TRUE;
    }

    private int manageRetries(List<StepAutomationDTO> automation, int i, StepAutomationDTO item, Boolean response) throws InterruptedException {
        if(!response){
            logger.debug("Tiene reintentos ".concat(item.getRetry()));
            logger.debug("Paso fallido ".concat(item.toString()));
            if("S".equalsIgnoreCase(item.getRetry())){
                logger.debug("Se ejecutara un reintento del paso ".concat(item.getLabelAccion()).concat(" en su ").concat(item.getCurrentNumbersOfRetries().toString()).concat("intento") );
                automation.get(i).setCurrentNumbersOfRetries(automation.get(i).getCurrentNumbersOfRetries()+1);
                i = item.getStepRetry().intValue() - 2;
                if(item.getNumberOfRetries() < item.getCurrentNumbersOfRetries()){
                    generateError();
                }
            }else{
                generateError();
            }
        }
        return i;
    }

    private void generateError() throws InterruptedException {
        this.finishTest = Boolean.FALSE;
        logger.error("Se finaliza la prueba por excepcion");
        this.managePictures.generateBugScreenShot();
        throw new InterruptedException("Se genero una excepcion en la ejecución de la prueba");
    }

    public Boolean initializeBrowser(){
        driver.get(url);
        logger.debug("Abrimos la URL: ".concat(url));
        driver.manage().window().maximize();
        return Boolean.TRUE;
    }

    public void validarInfoExcel(List<StepAutomationDTO> automation){
        logger.debug("Ini Informacion cargada desde excel");
        automation.forEach(step -> logger.debug(step.toString()));
        logger.debug("Fin Informacion cargada: Con ".concat(""+automation.size()).concat(" registros. "));
    }

    public Boolean getFinishTest() {
        return finishTest;
    }
}
