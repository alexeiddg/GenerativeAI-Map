package com.alexeiddg.backend.scraper.service;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.alexeiddg.backend.scraper.config.WebDriverFactory;
import com.alexeiddg.backend.scraper.utils.AITool;
import com.alexeiddg.backend.scraper.utils.CategoryLink;
import com.alexeiddg.backend.scraper.WebsiteScraper;

@Service
public class BatchProcessingService {

    private static final Logger logger = Logger.getLogger(BatchProcessingService.class.getName());
    private final WebDriverFactory webDriverFactory;
    private final HtmlParser htmlParser;
    private static final int DELAY_BETWEEN_REQUESTS_MS = 30000;

    @Autowired
    public BatchProcessingService(WebDriverFactory webDriverFactory, HtmlParser htmlParser) {
        this.webDriverFactory = webDriverFactory;
        this.htmlParser = htmlParser;
    }

    public List<AITool> scrapeToolsFromCategories(List<CategoryLink> categoryLinks) {
        List<AITool> aiTools = new ArrayList<>();
        for (CategoryLink link : categoryLinks) {
            WebDriver webDriver = webDriverFactory.createWebDriver();
            try {
                List<AITool> tools = WebsiteScraper.scrapeCategoryPage(webDriver, link.getCategoryUrl(), htmlParser);
                if (tools != null) {
                    aiTools.addAll(tools);
                }
                Thread.sleep(DELAY_BETWEEN_REQUESTS_MS);
            } catch (InterruptedException e) {
                logger.severe("Thread was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            } finally {
                if (webDriver != null) {
                    webDriver.quit();
                }
            }
        }
        return aiTools;
    }
}
