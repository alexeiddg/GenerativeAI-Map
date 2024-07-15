package com.alexeiddg.backend.scraper.utils;

public class AITool {
    private String name;
    private String description;
    private String url;
    private String rating;
    private String category;

    public AITool(String name, String description, String url, String rating, String category) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.rating = rating;
        this.category = category;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}

// TODO: Check if the AITool class is complete and correct -- some methods are unused