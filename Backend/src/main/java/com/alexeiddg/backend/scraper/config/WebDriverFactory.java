package com.alexeiddg.backend.scraper.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.alexeiddg.backend.scraper.AppConfig;

import java.util.Random;

@Component
@PropertySource("classpath:application.properties")
public class WebDriverFactory {

    private final AppConfig appConfig;
    private final Random random;

    @Autowired
    public WebDriverFactory(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.random = new Random();
    }

    public WebDriver createWebDriver() {
        System.setProperty("webdriver.chrome.driver", appConfig.getChromeDriverPath());
        System.setProperty("webdriver.chrome.binary", appConfig.getChromeBinaryPath());

        String userAgent = appConfig.getUserAgents().get(random.nextInt(appConfig.getUserAgents().size()));

        ChromeOptions options = new ChromeOptions();
        options.setBinary(appConfig.getChromeBinaryPath());
        options.addArguments("--headless");
        options.addArguments("user-agent=" + userAgent);
        return new ChromeDriver(options);
    }
}
