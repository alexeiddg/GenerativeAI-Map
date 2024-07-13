package com.alexeiddg.backend.scraper.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alexeiddg.backend.scraper.utils.AITool;
import com.alexeiddg.backend.scraper.utils.CategoryLink;
import com.alexeiddg.backend.scraper.AppConfig;

@Service
public class HtmlParser {
    private final Set<String> excludedCategories;

    @Autowired
    public HtmlParser(AppConfig appConfig) {
        this.excludedCategories = new HashSet<>(appConfig.getExcludedCategories());
    }

    public List<CategoryLink> parseHtmlForCategories(String html) {
        List<CategoryLink> categoryLinks = new ArrayList<>();
        Document document = Jsoup.parse(html);

        Elements categoryElements = document.select("div.grid-item div.blog-list");

        for (Element categoryElement : categoryElements) {
            String categoryName = categoryElement.select("span.title-list").text();
            String categoryUrl = categoryElement.select("a").attr("href");

            if (!excludedCategories.contains(categoryName)) {
                categoryLinks.add(new CategoryLink(categoryName, categoryUrl));
            }
        }

        return categoryLinks;
    }

    public List<AITool> parseHtmlForTools(String html, String category) {
        List<AITool> aiTools = new ArrayList<>();
        Document document = Jsoup.parse(html);

        Elements toolElements = document.select("div.post-info");

        for (Element toolElement : toolElements) {
            String name = toolElement.select("span.post-title a").text();
            String description = toolElement.select("p.post-excerpt").text();
            String url = toolElement.select("span.post-title a").attr("href");
            String rating = toolElement.select("div.kksr-legend").text().split("/")[0];

            aiTools.add(new AITool(name, description, url, rating, category));
        }

        return aiTools;
    }
}
