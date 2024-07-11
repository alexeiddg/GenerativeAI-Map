package com.alexeiddg.backend.scraper.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alexeiddg.backend.scraper.AppConfig;

@Configuration
@PropertySource("classpath:application.properties")
public class WebDriverConfig {

    private final AppConfig appConfig;

    public WebDriverConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Bean
    public WebDriver webDriver() {
        System.setProperty("webdriver.chrome.driver", appConfig.getChromeDriverPath());
        System.setProperty("webdriver.chrome.binary", appConfig.getChromeBinaryPath());

        ChromeOptions options = new ChromeOptions();
        options.setBinary(appConfig.getChromeBinaryPath());
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }
}

