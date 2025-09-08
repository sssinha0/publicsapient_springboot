package com.publicsapient.demo.service;

import com.publicsapient.demo.dto.RecipeDTO;
import com.publicsapient.demo.dto.RecipeListResponse;
import com.publicsapient.demo.exceptions.ExternalServiceException;
import com.publicsapient.demo.model.Recipe;
import com.publicsapient.demo.repository.RecipeRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.publicsapient.demo.utility.JsonUtils.convertListToJson;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeOrchestrationService {
    private final WebClient webClient; // configured bean
    private final RecipeRepository repo;
    private final RecipeSearchService searchService;

    @CircuitBreaker(name = "recipesClient", fallbackMethod = "fallbackLoadAll")
    @Retry(name = "recipesClient")
    public void loadAllRecipes() {
        // DummyJSON docs: default 30 items, supports limit & skip pagination.
        // We'll fetch pages until returned size < pageSize
        int limit = 100;
        int skip = 0;
        List<Recipe> all = new ArrayList<>();
        while (true) {
            int finalSkip = skip;
            RecipeListResponse resp = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/recipes")
                            .queryParam("limit", limit)
                            .queryParam("skip", finalSkip)
                            .build())
                    .retrieve()
                    .bodyToMono(RecipeListResponse.class)
                    .block(Duration.ofSeconds(10));

            if (resp == null || resp.getRecipes().isEmpty()) break;

            List<Recipe> mapped = resp.getRecipes().stream()
                    .map(this::mapDtoToEntity)
                    .collect(Collectors.toList());
            repo.saveAll(mapped);

            if (resp.getRecipes().size() < limit) break;
            skip += limit;
        }

    }

    public void fallbackLoadAll(Throwable t) {
        // log and rethrow or take alternative action
        log.error("Failed to load recipes: {}", t.toString());
        throw new ExternalServiceException("Unable to load recipes at this time", t);
    }

    public Recipe mapDtoToEntity(RecipeDTO dto) {
        if (dto == null) {
            return null;
        }
        Recipe r = new Recipe();
        r.setId(dto.getId());
        r.setName(dto.getName());
        r.setCuisine(dto.getCuisine());
        r.setInstructions(dto.getInstructions());
        r.setIngredients(dto.getIngredients());
        r.setServings(dto.getServings());
        r.setPrepTimeMinutes(dto.getPrepTimeMinutes());
        r.setCookTimeMinutes(dto.getCookTimeMinutes());
        r.setDifficulty(dto.getDifficulty());
        r.setCaloriesPerServing(dto.getCaloriesPerServing());
        r.setTags(dto.getTags());
        r.setUserId(dto.getUserId());
        r.setImage(dto.getImage());
        r.setRating(dto.getRating());
        r.setReviewCount(dto.getReviewCount());
        r.setMealType(dto.getMealType());

        return r;
    }

}

