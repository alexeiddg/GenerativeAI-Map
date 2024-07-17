package com.alexeiddg.backend.scraper.service;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.alexeiddg.backend.scraper.AppConfig;
import com.alexeiddg.backend.scraper.config.WebDriverFactory;
import com.alexeiddg.backend.scraper.utils.AITool;
import com.alexeiddg.backend.scraper.utils.CategoryLink;
import com.alexeiddg.backend.scraper.WebsiteScraper;
import com.alexeiddg.backend.api.service.AIToolService;

@Service
public class WebScrapingService {

    private final HtmlParser htmlParser;
    private final AppConfig appConfig;
    private final BatchProcessingService batchProcessingService;
    private final AIToolService aiToolService;

    @Autowired
    public WebScrapingService(
            HtmlParser htmlParser,
            AppConfig appConfig,
            BatchProcessingService batchProcessingService,
            AIToolService aiToolService

    ) {
        this.htmlParser = htmlParser;
        this.appConfig = appConfig;
        this.batchProcessingService = batchProcessingService;
        this.aiToolService = aiToolService;
    }

    public void scrapeAndProcess(int categoryLimit, int pageLimit) {
        List<CategoryLink> categoryLinks = scrapeWebsite();
        if (categoryLinks != null) {
            List<AITool> scrapedTools = batchProcessingService.scrapeToolsFromCategories(categoryLinks, categoryLimit, pageLimit);
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
        aiToolService.saveAITools(aiTools);
    }
}
