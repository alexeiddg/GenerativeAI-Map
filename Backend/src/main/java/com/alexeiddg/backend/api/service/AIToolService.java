package com.alexeiddg.backend.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alexeiddg.backend.api.model.AIToolModel;
import com.alexeiddg.backend.repository.AIToolRepository;
import com.alexeiddg.backend.scraper.utils.AITool;
import com.alexeiddg.backend.scraper.utils.AIToolConverter;

@Service
public class AIToolService {

    private static final Logger logger = Logger.getLogger(AIToolService.class.getName());

    private final AIToolRepository aiToolRepository;

    @Autowired
    public AIToolService(AIToolRepository aiToolRepository) {
        this.aiToolRepository = aiToolRepository;
    }

    @Transactional
    public void saveAITools(List<AITool> aiTools) {
        int batchSize = 13;
        List<AIToolModel> batch = new ArrayList<>();

        for (AITool tool : aiTools) {
            AIToolModel model = AIToolConverter.toModel(tool);
            try {
                Optional<AIToolModel> existingTool = aiToolRepository.findByNameAndUrl(tool.getName(), tool.getUrl());
                if (existingTool.isPresent()) {
                    AIToolModel existingModel = existingTool.get();
                    existingModel.setCategory(model.getCategory());
                    existingModel.setDescription(model.getDescription());
                    existingModel.setRating(model.getRating());
                    batch.add(existingModel);
                } else {
                    batch.add(model);
                }

                if (batch.size() == batchSize) {
                    aiToolRepository.saveAll(batch);
                    batch.clear();
                }

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Database error while processing tool: " + tool.getName() + " URL: " + tool.getUrl() + " Error: " + e.getMessage(), e);
            }
        }

        if (!batch.isEmpty()) {
            aiToolRepository.saveAll(batch);
        }
    }
}
