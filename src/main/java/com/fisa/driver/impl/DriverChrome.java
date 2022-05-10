package com.fisa.driver.impl;

import com.fisa.driver.DriverBrowser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverChrome implements DriverBrowser {

    private static final String WEB_DRIVER_PARAM = "webdriver.chrome.driver";
    private String path_driver;

    private WebDriver webDriver;

    private Integer idTransaccional;

    public DriverChrome(String path_driver, Integer idTransaccional) {
        this.idTransaccional = idTransaccional;
        this.path_driver = path_driver;
        System.setProperty(WEB_DRIVER_PARAM, path_driver);
        String fileNameLog = "omnia_spring"+ idTransaccional+".log";
        System.setProperty("LOG_FILE_NAME", fileNameLog);
        webDriver = new ChromeDriver();
    }
    @Override
    public WebDriver getDriver() {
        return webDriver;
    }

    @Override
    public Integer getIdTransaccional() {
        return idTransaccional;
    }
}
