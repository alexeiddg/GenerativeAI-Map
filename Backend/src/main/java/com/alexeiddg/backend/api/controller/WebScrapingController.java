package com.alexeiddg.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexeiddg.backend.scraper.service.WebScrapingService;

@RestController
public class WebScrapingController {

    private final WebScrapingService webScrapingService;

    @Autowired
    public WebScrapingController(WebScrapingService webScrapingService) {
        this.webScrapingService = webScrapingService;
    }

    @PostMapping("/api/update")
    public void scrapeWebsite() {
        webScrapingService.scrapeAndProcess(1, 3);
    }
}
