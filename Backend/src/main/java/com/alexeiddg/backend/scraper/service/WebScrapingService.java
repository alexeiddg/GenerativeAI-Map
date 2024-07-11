package com.alexeiddg.backend.scraper.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexeiddg.backend.scraper.utils.CategoryLink;
import com.alexeiddg.backend.scraper.AppConfig;

@Service
public class WebScrapingService {

    private static final Logger logger = Logger.getLogger(WebScrapingService.class.getName());
    private final WebDriver webDriver;
    private final HtmlParser htmlParser;
    private final AppConfig appConfig;

    @Autowired
    public WebScrapingService(WebDriver webDriver, HtmlParser htmlParser, AppConfig appConfig) {
        this.webDriver = webDriver;
        this.htmlParser = htmlParser;
        this.appConfig = appConfig;
    }

    public List<CategoryLink> scrapeWebsite() {
        List<CategoryLink> categoryLinks = null;

        try {
            webDriver.get(appConfig.getWebsiteUrl());

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(90));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));

            String pageSource = webDriver.getPageSource();
            categoryLinks = htmlParser.parseHtmlForCategories(pageSource);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while scraping the website", e);
        } finally {
            webDriver.quit();
        }

        return categoryLinks;
    }
}
