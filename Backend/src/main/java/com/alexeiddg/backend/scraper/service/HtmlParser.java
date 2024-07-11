package com.alexeiddg.backend.scraper.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexeiddg.backend.scraper.utils.CategoryLink;
import com.alexeiddg.backend.scraper.AppConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
