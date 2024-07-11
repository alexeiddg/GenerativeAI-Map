package com.alexeiddg.backend.scraper.utils;

public class CategoryLink {
    private String categoryName;
    private String categoryUrl;

    public CategoryLink(String categoryName, String categoryUrl) {
        this.categoryName = categoryName;
        this.categoryUrl = categoryUrl;
    }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getCategoryUrl() { return categoryUrl; }
    public void setCategoryUrl(String categoryUrl) { this.categoryUrl = categoryUrl; }
}

