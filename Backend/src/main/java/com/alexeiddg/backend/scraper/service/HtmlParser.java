package com.alexeiddg.backend.scraper.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.alexeiddg.backend.scraper.utils.AITool;

@Service
public class HtmlParser {

    public List<AITool> parseHtml(String html) {
        List<AITool> aiTools = new ArrayList<>();
        Document document = Jsoup.parse(html);

        Elements toolElements = document.select("div.post-item");
        for (Element toolElement : toolElements) {
            String name = toolElement.select("a.dark-title").text();
            String description = toolElement.select("p.post-excerpt").text();
            String url = toolElement.select("a.dark-title").attr("href");
            String rating = toolElement.select("div.kksr-legend").text();
            Elements categories = toolElement.select("span.post-category a");
            List<String> categoryList = new ArrayList<>();

            for (Element category : categories) {
                categoryList.add(category.text());
            }

            aiTools.add(new AITool(name, description, url, rating, categoryList));
        }

        return aiTools;
    }
}
