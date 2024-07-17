package com.alexeiddg.backend.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.alexeiddg.backend.api.model.AIToolModel;
import com.alexeiddg.backend.repository.AIToolRepository;
import com.alexeiddg.backend.scraper.utils.AITool;
import com.alexeiddg.backend.scraper.utils.AIToolConverter;

@Service
public class AIToolService {

    private static final Logger logger = Logger.getLogger(AIToolService.class.getName());
    private AIToolRepository aiToolRepository;

    @Autowired
    public AIToolService(AIToolRepository aiToolRepository) {
        this.aiToolRepository = aiToolRepository;
    }

    @Transactional
    public void saveAITools(List<AITool> aiTools) {
        for (AITool tool : aiTools) {
            AIToolModel model = AIToolConverter.toModel(tool);
            try {
                Optional<AIToolModel> existingTool = aiToolRepository.findByNameAndUrl(tool.getName(), tool.getUrl());
                if (existingTool.isPresent()) {
                    logger.info("Tool already exists: " + tool.getName() + " URL: " + tool.getUrl());
                } else {
                    aiToolRepository.save(model);
                    logger.info("Successfully saved tool: " + tool.getName() + " URL: " + tool.getUrl());
                }
            } catch (DataAccessException e) {
                logger.warning("Database error while saving tool: " + tool.getName() + " URL: " + tool.getUrl() + " Error: " + e.getMessage());
                continue; // Continue with the next tool
            } catch (Exception e) {
                logger.warning("Error saving tool: " + tool.getName() + " URL: " + tool.getUrl() + " Error: " + e.getMessage());
                continue; // Continue with the next tool
            }
        }
    }
}
