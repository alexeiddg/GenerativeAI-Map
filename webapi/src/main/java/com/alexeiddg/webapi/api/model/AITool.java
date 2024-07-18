package com.alexeiddg.webapi.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ai_tools", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "url"})})
public class AITool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 5000)
    private String description;

    @Column(nullable = false)
    private String url;
    private String rating;
    private String category;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url;}
    public void setUrl(String url) { this.url = url; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
