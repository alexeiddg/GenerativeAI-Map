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

import com.alexeiddg.backend.scraper.utils.AITool;
import com.alexeiddg.backend.scraper.utils.HtmlFileWriter;

@Service
public class WebScrapingService {

    private static final Logger logger = Logger.getLogger(WebScrapingService.class.getName());
    private final WebDriver webDriver;
    private final HtmlParser htmlParser;

    @Autowired
    public WebScrapingService(WebDriver webDriver, HtmlParser htmlParser) {
        this.webDriver = webDriver;
        this.htmlParser = htmlParser;
    }

    public List<AITool> scrapeWebsite() {
        List<AITool> aiTools = null;

        try {
            webDriver.get("https://www.aixploria.com/en/ultimate-list-ai/");

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(90));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));

            String pageSource = webDriver.getPageSource();
            aiTools = htmlParser.parseHtml(pageSource);

            // HtmlFileWriter.saveHtmlToFile(pageSource, "webpage_content.txt");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while scraping the website", e);
        } finally {
            webDriver.quit();
        }

        return aiTools;
    }
}


