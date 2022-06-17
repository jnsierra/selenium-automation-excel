package com.fisa.driver.impl;

import com.fisa.driver.DriverBrowser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFirefox implements DriverBrowser {

    private static final String WEB_DRIVER_PARAM = "webdriver.gecko.driver";
    private String path_driver;
    private Integer idTransaccional;
    private WebDriver webDriver;

    public DriverFirefox(String path_driver, Integer idTransaccional) {
        this.path_driver = path_driver;
        this.idTransaccional = idTransaccional;
        System.setProperty(WEB_DRIVER_PARAM, path_driver);
        String fileNameLog = "omnia_spring"+ idTransaccional+".log";
        String fileNameError = "error_omnia_spring"+ idTransaccional+".txt";
        System.setProperty("LOG_FILE_NAME", fileNameLog);
        System.setProperty("LOG_FILE_NAME_ERROR", fileNameError);
        this.webDriver = new FirefoxDriver();
    }

    @Override
    public WebDriver getDriver() {
        return this.webDriver;
    }

    @Override
    public Integer getIdTransaccional() {
        return this.idTransaccional;
    }
}
