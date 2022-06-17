package com.fisa.config;

import com.fisa.driver.DriverBrowser;
import com.fisa.driver.impl.DriverChrome;
import com.fisa.driver.impl.DriverFirefox;
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
    @Value("${selenium.path.chrome}")
    private String PATH_DRIVER_CHROME;
    @Value("${selenium.path.firefox}")
    private String PATH_DRIVER_FIREFOX;

    private Integer idTransaccion;

    @Bean("getDriver")
    @Scope("singleton")
    public WebDriver getDriver(){
        Random random = new Random();
        DriverBrowser driver = null;
        if("chrome".equalsIgnoreCase(browser)){
            driver = new DriverChrome(PATH_DRIVER_CHROME, Math.abs(random.nextInt()));
        }else if("firefox".equalsIgnoreCase(browser)){
            driver = new DriverFirefox(PATH_DRIVER_FIREFOX, Math.abs(random.nextInt()));
        }
        this.idTransaccion = driver.getIdTransaccional();
        return driver.getDriver();
    }
    @Bean("IdTransaccional")
    @Scope("singleton")
    public Integer getIdTransaccional(){
        return this.idTransaccion;
    }

}