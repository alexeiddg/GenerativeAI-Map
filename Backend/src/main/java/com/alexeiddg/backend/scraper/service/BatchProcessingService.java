package com.alexeiddg.backend.scraper.service;

import com.alexeiddg.backend.scraper.config.WebDriverFactory;
import com.alexeiddg.backend.scraper.utils.AITool;
import com.alexeiddg.backend.scraper.utils.CategoryLink;
import com.alexeiddg.backend.scraper.WebsiteScraper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BatchProcessingService {

    private static final Logger logger = Logger.getLogger(BatchProcessingService.class.getName());
    private final WebDriverFactory webDriverFactory;
    private final HtmlParser htmlParser;
    private static final int MIN_DELAY_BETWEEN_REQUESTS_MS = 30000;
    private static final int MAX_DELAY_BETWEEN_REQUESTS_MS = 60000;

    @Autowired
    public BatchProcessingService(WebDriverFactory webDriverFactory, HtmlParser htmlParser) {
        this.webDriverFactory = webDriverFactory;
        this.htmlParser = htmlParser;
    }

    public List<AITool> scrapeToolsFromCategories(List<CategoryLink> categoryLinks) {
        List<AITool> aiTools = new ArrayList<>();
        int limit = Math.min(categoryLinks.size(), 1); // Limit to first 2 pages for testing

        for (int i = 0; i < limit; i++) {
            CategoryLink link = categoryLinks.get(i);
            WebDriver webDriver = webDriverFactory.createWebDriver();
            try {
                logger.info("Starting scraping for category: " + link.categoryName() + " URL: " + link.categoryUrl());
                List<AITool> tools = scrapeAllPagesInCategory(webDriver, link.categoryUrl(), link.categoryName());
                if (tools != null) {
                    for (AITool tool : tools) {
                        tool.setCategory(link.categoryName());
                    }
                    aiTools.addAll(tools);
                }
                // Introduce a delay between requests
                int delay = ThreadLocalRandom.current().nextInt(MIN_DELAY_BETWEEN_REQUESTS_MS, MAX_DELAY_BETWEEN_REQUESTS_MS + 1);
                logger.info("Waiting for " + (delay / 1000) + " seconds before starting the next scrape.");
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                logger.severe("Thread was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt(); // Restore the interrupted status
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

    private List<AITool> scrapeAllPagesInCategory(WebDriver webDriver, String url, String categoryName) {
        List<AITool> aiTools = new ArrayList<>();
        boolean hasNextPage = true;
        String currentUrl = url;
        int pageNumber = 1;

        while (hasNextPage) {
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

                // Check for next page link
                String nextPageUrl = getNextPageUrl(webDriver);
                if (nextPageUrl != null) {
                    currentUrl = nextPageUrl;
                    pageNumber++;
                } else {
                    hasNextPage = false;
                    logger.info("No next page found.");
                }

                // Introduce a delay between requests
                int delay = ThreadLocalRandom.current().nextInt(MIN_DELAY_BETWEEN_REQUESTS_MS, MAX_DELAY_BETWEEN_REQUESTS_MS + 1);
                logger.info("Waiting for " + (delay / 1000) + " seconds before scraping the next page.");
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                logger.severe("Thread was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt(); // Restore the interrupted status
                break;
            } catch (Exception e) {
                logger.severe("Exception occurred while scraping category page: " + e.getMessage());
                break;
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
