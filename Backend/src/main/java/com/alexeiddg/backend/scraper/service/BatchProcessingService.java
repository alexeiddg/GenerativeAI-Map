package com.alexeiddg.backend.scraper.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.concurrent.ThreadLocalRandom;

import com.alexeiddg.backend.scraper.utils.AITool;
import com.alexeiddg.backend.scraper.utils.CategoryLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchProcessingService {

    private static final Logger logger = Logger.getLogger(BatchProcessingService.class.getName());
    private final CategoryScraper categoryScraper;
    private static final int MIN_DELAY_BETWEEN_REQUESTS_MS = 30000;
    private static final int MAX_DELAY_BETWEEN_REQUESTS_MS = 60000;

    @Autowired
    public BatchProcessingService(CategoryScraper categoryScraper) {
        this.categoryScraper = categoryScraper;
    }

    public List<AITool> scrapeToolsFromCategories(List<CategoryLink> categoryLinks, int categoryLimit, int pageLimit) {
        Set<AITool> uniqueTools = new HashSet<>();
        int limit = Math.min(categoryLinks.size(), categoryLimit);

        for (int i = 0; i < limit; i++) {
            CategoryLink link = categoryLinks.get(i);

            logger.info("Starting scraping for category: " + link.categoryName() + " URL: " + link.categoryUrl());

            List<AITool> tools = categoryScraper.scrapeCategory(link.categoryUrl(), link.categoryName(), pageLimit);
            if (tools != null) {
                uniqueTools.addAll(tools);
            }

            int delay = ThreadLocalRandom.current().nextInt(MIN_DELAY_BETWEEN_REQUESTS_MS, MAX_DELAY_BETWEEN_REQUESTS_MS + 1);
            logger.info("Waiting for " + (delay / 1000) + " seconds before starting the next scrape.");

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                logger.severe("Thread was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        return new ArrayList<>(uniqueTools);
    }
}
