package com.alexeiddg.backend.scraper.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class WebScrapingService {

    private static final Logger logger = Logger.getLogger(WebScrapingService.class.getName());
    private final WebDriver webDriver;

    @Autowired
    public WebScrapingService(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public List<String> scrapeWebsite() {
        List<String> scrapedData = new ArrayList<>();

        try {
            webDriver.get("https://www.aixploria.com/en/ultimate-list-ai/");

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));

            String pageSource = webDriver.getPageSource();
            scrapedData.add(pageSource);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while scraping the website", e);
        } finally {
            webDriver.quit();
        }

        return scrapedData;
    }
}
