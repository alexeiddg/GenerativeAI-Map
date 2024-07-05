package com.alexeiddg.backend.api.controller;

import com.alexeiddg.backend.scraper.service.WebScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.alexeiddg.backend.scraper.utils.AITool;

@RestController
@RequestMapping("/api/scrape")
public class WebScrapingController {

    private final WebScrapingService webScrapingService;

    @Autowired
    public WebScrapingController(WebScrapingService webScrapingService) {
        this.webScrapingService = webScrapingService;
    }

    @GetMapping
    public List<AITool> scrape() {
        return webScrapingService.scrapeWebsite();
    }
}
