package com.alexeiddg.backend.scraper.utils;

import com.alexeiddg.backend.api.model.AIToolModel;

public class AIToolConverter {
    public static AIToolModel toModel(AITool aiTool) {
        AIToolModel model = new AIToolModel();
        model.setName(aiTool.getName());
        model.setDescription(aiTool.getDescription());
        model.setUrl(aiTool.getUrl());
        model.setRating(aiTool.getRating());
        model.setCategory(aiTool.getCategory());
        return model;
    }

    public static AITool fromModel(AIToolModel model) {
        return new AITool(
                model.getName(),
                model.getDescription(),
                model.getUrl(),
                model.getRating(),
                model.getCategory()
        );
    }
}
