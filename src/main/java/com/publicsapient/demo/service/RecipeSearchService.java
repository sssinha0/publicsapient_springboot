package com.publicsapient.demo.service;

import com.publicsapient.demo.model.Recipe;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeSearchService {
    private final EntityManager entityManager;

    public List<Recipe> search(String text, int page, int size) {
        SearchSession searchSession = Search.session(entityManager);

        return searchSession.search(Recipe.class)
                .where(f -> f.simpleQueryString()
                        .fields("name", "cuisine")
                        .matching(text == null ? "" : text))
                .sort(f -> f.score())
                .fetchHits(page * size, size);
    }
}

