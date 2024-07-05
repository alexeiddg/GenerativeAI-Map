package com.alexeiddg.backend.scraper.utils;

import java.util.List;

public class AITool {
    private String name;
    private String description;
    private String url;
    private String rating;
    private List<String> categories;

    public AITool(String name, String description, String url, String rating, List<String> categories) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.rating = rating;
        this.categories = categories;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
}

