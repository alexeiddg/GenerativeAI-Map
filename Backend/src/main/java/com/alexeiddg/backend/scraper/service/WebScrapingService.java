package com.alexeiddg.backend.scraper.service;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import com.alexeiddg.backend.scraper.AppConfig;
import com.alexeiddg.backend.scraper.config.WebDriverFactory;
import com.alexeiddg.backend.scraper.utils.AITool;
import com.alexeiddg.backend.scraper.utils.CategoryLink;
import com.alexeiddg.backend.scraper.WebsiteScraper;

@Service
public class WebScrapingService {

    private static final Logger logger = Logger.getLogger(WebScrapingService.class.getName());
    private final HtmlParser htmlParser;
    private final AppConfig appConfig;
    private final BatchProcessingService batchProcessingService;

    private List<AITool> scrapedTools;

    @Autowired
    public WebScrapingService(
            HtmlParser htmlParser,
            AppConfig appConfig,
            BatchProcessingService batchProcessingService
    ) {
        this.htmlParser = htmlParser;
        this.appConfig = appConfig;
        this.batchProcessingService = batchProcessingService;
    }

    public void scrapeAndProcess() {
        List<CategoryLink> categoryLinks = scrapeWebsite();
        if (categoryLinks != null) {
            scrapedTools = batchProcessingService.scrapeToolsFromCategories(categoryLinks);
            processAITools(scrapedTools);
        }
    }

    private List<CategoryLink> scrapeWebsite() {
        WebDriver webDriver = new WebDriverFactory(appConfig).createWebDriver();
        try {
            return WebsiteScraper.scrapeWebsite(webDriver, appConfig.getWebsiteUrl(), htmlParser);
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }

    private void processAITools(List<AITool> aiTools) {
        // WIP
    }

    public List<AITool> getScrapedTools() {
        return scrapedTools;
    }
}
