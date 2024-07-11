package com.alexeiddg.backend.api.controller;

import com.alexeiddg.backend.scraper.service.WebScrapingService;
import com.alexeiddg.backend.scraper.utils.CategoryLink;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebScrapingController {

    private final WebScrapingService webScrapingService;

    public WebScrapingController(WebScrapingService webScrapingService) {
        this.webScrapingService = webScrapingService;
    }

    @GetMapping("/api/scrape")
    public List<CategoryLink> scrapeWebsite() {
        return webScrapingService.scrapeWebsite();
    }
}


