package com.alexeiddg.backend.scraper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AppConfig {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriverPath;

    @Value("${webdriver.chrome.binary}")
    private String chromeBinaryPath;

    @Value("${website.url}")
    private String websiteUrl;

    @Value("${excluded.categories}")
    private String excludedCategories;

    @Value("${user.agents}")
    private String userAgents;

    public String getChromeDriverPath() { return chromeDriverPath; }

    public String getChromeBinaryPath() { return chromeBinaryPath; }

    public String getWebsiteUrl() { return websiteUrl; }

    public List<String> getExcludedCategories() {
        return Arrays.asList(excludedCategories.split(","));
    }

    public List<String> getUserAgents() {
        return Arrays.asList(userAgents.split(","));
    }
}
