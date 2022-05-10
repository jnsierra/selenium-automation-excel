package com.fisa.config;

import com.fisa.driver.impl.DriverChrome;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Random;

@Configuration
public class SeleniumConfig {

    @Value("${selenium.browser}")
    private String browser;
    @Value("${selenium.path}")
    private String PATH_DRIVER;

    private Integer idTransaccion;

    @Bean("getDriver")
    @Scope("singleton")
    public WebDriver getDriver(){
        Random random = new Random();
        if("chrome".equalsIgnoreCase(browser)){
            DriverChrome driverChrome = new DriverChrome(PATH_DRIVER, Math.abs(random.nextInt()));
            this.idTransaccion = driverChrome.getIdTransaccional();
            return driverChrome.getDriver();
        }
        return null;
    }
    @Bean("IdTransaccional")
    @Scope("singleton")
    public Integer getIdTransaccional(){
        return this.idTransaccion;
    }

}