package com.fisa.service;

import com.fisa.dto.StepAutomationDTO;
import org.openqa.selenium.WebElement;

import java.util.Optional;

public interface ManageWaits {

    Optional< WebElement > waitAndReturnElement(StepAutomationDTO stepAutomationDTO)throws InterruptedException;

}