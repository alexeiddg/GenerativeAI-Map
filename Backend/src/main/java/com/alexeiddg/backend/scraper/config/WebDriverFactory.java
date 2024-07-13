package com.alexeiddg.backend.scraper.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alexeiddg.backend.scraper.AppConfig;

@Component
public class WebDriverFactory {

    private final AppConfig appConfig;

    @Autowired
    public WebDriverFactory(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public WebDriver createWebDriver() {
        System.setProperty("webdriver.chrome.driver", appConfig.getChromeDriverPath());
        System.setProperty("webdriver.chrome.binary", appConfig.getChromeBinaryPath());

        ChromeOptions options = new ChromeOptions();
        options.setBinary(appConfig.getChromeBinaryPath());
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }
}
