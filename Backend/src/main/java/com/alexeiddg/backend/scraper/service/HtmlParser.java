package com.alexeiddg.backend.scraper.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.alexeiddg.backend.scraper.utils.AITool;

import java.util.ArrayList;
import java.util.List;

@Service
public class HtmlParser {

    public List<AITool> parseHtml(String html) {
        List<AITool> aiTools = new ArrayList<>();
        Document document = Jsoup.parse(html);

        // Select the grid items that contain AI tools
        Elements toolElements = document.select("div.grid-item div.blog-list ol li");

        for (Element toolElement : toolElements) {
            String name = toolElement.select("a.tooltips").text();

            // Currently fetches the internal URL of the tool and not the public URL
            String url = toolElement.select("a.tooltips").attr("href");

            // Currently does not fetch these fields
            String description = toolElement.select("a.tooltips").attr("title");
            String rating = toolElement.select("div.kksr-legend").text();
            String category = toolElement.select("span.hashtaga").text();

            aiTools.add(new AITool(name, description, url, rating, category));
        }

        return aiTools;
    }
}
