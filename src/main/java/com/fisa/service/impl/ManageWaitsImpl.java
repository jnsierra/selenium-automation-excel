package com.fisa.service.impl;

import com.fisa.dto.StepAutomationDTO;
import com.fisa.enumeration.TypeAdditionalWait;
import com.fisa.enumeration.TypeFindByEnum;
import com.fisa.service.ManageWaits;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ManageWaitsImpl implements ManageWaits {

    private WebDriver driver;

    @Autowired
    public ManageWaitsImpl(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public Optional<WebElement> waitAndReturnElement(StepAutomationDTO stepAutomationDTO) throws InterruptedException {
        sleepTime(stepAutomationDTO);
        WebElement elemnt = this.waitFluent(stepAutomationDTO);
        Boolean response = this.generateExplicit(stepAutomationDTO.getTimeAdditional(), elemnt,stepAutomationDTO);
        if(response)
            return Optional.of(elemnt);
        return Optional.empty();
    }

    private void sleepTime(StepAutomationDTO step) throws InterruptedException {
        Thread.sleep(step.getSleepBefore() * 1000 );
    }

    private WebElement waitFluent(StepAutomationDTO stepAutomationDTO){
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(stepAutomationDTO.getTimeOutWait()))
                .pollingEvery(Duration.ofSeconds(stepAutomationDTO.getRepeatTime()))
                .ignoring(NoSuchElementException.class);
        WebElement usernameElement = wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                if(TypeFindByEnum.XPATH.equals(stepAutomationDTO.getTypeFindByEnum())){
                    return driver.findElement(By.xpath(stepAutomationDTO.getFindBy()));
                } else if (TypeFindByEnum.CLASSNAME.equals(stepAutomationDTO.getTypeFindByEnum())) {
                    return driver.findElement(By.className(stepAutomationDTO.getFindBy()));
                }
                return driver.findElement(By.id(stepAutomationDTO.getFindBy()));
            }
        });
        return usernameElement;
    }

    private Boolean generateExplicit(Long timeOut, WebElement element, StepAutomationDTO step)throws InterruptedException{
        if(TypeAdditionalWait.CLICKABLE.equals(step.getAdditionalTypeWait())){
            WebDriverWait waitT = new WebDriverWait(driver, timeOut);
            waitT.until(ExpectedConditions.elementToBeClickable(element));
        }
        Thread.sleep(500);
        return Boolean.TRUE;
    }
}