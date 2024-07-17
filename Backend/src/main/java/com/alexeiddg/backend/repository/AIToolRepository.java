package com.alexeiddg.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.alexeiddg.backend.api.model.AIToolModel;

@Repository
public interface AIToolRepository extends JpaRepository<AIToolModel, Long> {
    Optional<AIToolModel> findByNameAndUrl(String name, String url);
}

