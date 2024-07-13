package com.alexeiddg.backend.scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexeiddg.backend.scraper.utils.AITool;
import com.alexeiddg.backend.scraper.utils.CategoryLink;
import com.alexeiddg.backend.scraper.service.HtmlParser;

public class WebsiteScraper {

    private static final Logger logger = Logger.getLogger(WebsiteScraper.class.getName());

    public static List<CategoryLink> scrapeWebsite(WebDriver webDriver, String url, HtmlParser htmlParser) {
        List<CategoryLink> categoryLinks = null;

        try {
            if (webDriver != null) {
                webDriver.get(url);

                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(90));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));

                String pageSource = webDriver.getPageSource();
                categoryLinks = htmlParser.parseHtmlForCategories(pageSource);
            } else {
                logger.severe("WebDriver is not initialized properly.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred while scraping website", e);
        } finally {
            if (webDriver != null) {
                try {
                    webDriver.quit();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Exception occurred while quitting WebDriver", e);
                }
            }
        }

        return categoryLinks;
    }

    public static List<AITool> scrapeCategoryPage(WebDriver webDriver, String url, HtmlParser htmlParser) {
        List<AITool> aiTools = null;

        try {
            if (webDriver != null) {
                webDriver.get(url);

                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(90));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));

                String pageSource = webDriver.getPageSource();
                aiTools = htmlParser.parseHtmlForTools(pageSource, url);
            } else {
                logger.severe("WebDriver is not initialized properly.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred while scraping category page", e);
        }

        return aiTools;
    }
}
