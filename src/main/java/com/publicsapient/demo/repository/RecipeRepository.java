package com.publicsapient.demo.repository;

import com.publicsapient.demo.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // You can keep JPA methods for CRUD; full-text queries done via Hibernate Search API.
}

