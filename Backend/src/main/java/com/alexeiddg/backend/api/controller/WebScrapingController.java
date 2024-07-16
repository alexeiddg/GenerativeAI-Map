package com.alexeiddg.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.alexeiddg.backend.scraper.service.WebScrapingService;
import com.alexeiddg.backend.scraper.utils.AITool;

@RestController
public class WebScrapingController {

    private final WebScrapingService webScrapingService;

    @Autowired
    public WebScrapingController(WebScrapingService webScrapingService) {
        this.webScrapingService = webScrapingService;
    }

    @GetMapping("/api/scrape")
    public List<AITool> scrapeWebsite() {
        webScrapingService.scrapeAndProcess(1, 1);
        return webScrapingService.getScrapedTools();
    }
}
