package com.alexeiddg.backend.scraper.service;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.concurrent.ThreadLocalRandom;

import com.alexeiddg.backend.scraper.WebsiteScraper;
import com.alexeiddg.backend.scraper.config.WebDriverFactory;
import com.alexeiddg.backend.scraper.utils.AITool;

@Component
public class CategoryScraper {

    private static final Logger logger = Logger.getLogger(CategoryScraper.class.getName());
    private final WebDriverFactory webDriverFactory;
    private final HtmlParser htmlParser;
    private static final int MIN_DELAY_BETWEEN_REQUESTS_MS = 30000;
    private static final int MAX_DELAY_BETWEEN_REQUESTS_MS = 60000;

    @Autowired
    public CategoryScraper(WebDriverFactory webDriverFactory, HtmlParser htmlParser) {
        this.webDriverFactory = webDriverFactory;
        this.htmlParser = htmlParser;
    }

    public List<AITool> scrapeCategory(String url, String categoryName, int pageLimit) {
        List<AITool> aiTools = new ArrayList<>();
        String currentUrl = url;
        boolean hasNextPage = true;
        int pageNumber = 1;

        while (hasNextPage && pageNumber <= pageLimit) {
            WebDriver webDriver = webDriverFactory.createWebDriver();

            try {
                logger.info("Scraping page " + pageNumber + " for category: " + categoryName + " URL: " + currentUrl);

                webDriver.get(currentUrl);
                List<AITool> tools = WebsiteScraper.scrapeCategoryPage(webDriver, currentUrl, htmlParser);
                if (tools != null) {
                    aiTools.addAll(tools);
                    logger.info("Scraped " + tools.size() + " tools from page " + pageNumber);
                } else {
                    logger.warning("No tools found on page " + pageNumber);
                }

                String nextPageUrl = getNextPageUrl(webDriver);
                if (nextPageUrl != null) {
                    currentUrl = nextPageUrl;
                    pageNumber++;
                } else {
                    hasNextPage = false;
                }

                int delay = ThreadLocalRandom.current().nextInt(MIN_DELAY_BETWEEN_REQUESTS_MS, MAX_DELAY_BETWEEN_REQUESTS_MS + 1);
                logger.info("Waiting for " + (delay / 1000) + " seconds before scraping the next page.");
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                logger.severe("Thread was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                logger.severe("Exception occurred while scraping category page: " + e.getMessage());
                break;
            } finally {
                if (webDriver != null) {
                    try {
                        webDriver.quit();
                    } catch (Exception e) {
                        logger.severe("Exception occurred while quitting WebDriver: " + e.getMessage());
                    }
                }
            }
        }
        return aiTools;
    }

    private String getNextPageUrl(WebDriver webDriver) {
        try {
            WebElement nextPageElement = webDriver.findElement(By.cssSelector(".page-nav .next.page-numbers"));
            if (nextPageElement != null) {
                return nextPageElement.getAttribute("href");
            }
        } catch (NoSuchElementException e) {
            logger.info("No next page found.");
        }
        return null;
    }
}
