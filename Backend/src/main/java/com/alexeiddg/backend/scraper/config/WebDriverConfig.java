package com.alexeiddg.backend.scraper.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class WebDriverConfig {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriverPath;

    @Value("${webdriver.chrome.binary}")
    private String chromeBinaryPath;

    @Bean
    public WebDriver webDriver() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.setBinary(chromeBinaryPath);
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }
}
