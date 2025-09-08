package com.publicsapient.demo.controller;

import com.publicsapient.demo.exceptions.ResourceNotFoundException;
import com.publicsapient.demo.model.Recipe;
import com.publicsapient.demo.repository.RecipeRepository;
import com.publicsapient.demo.service.RecipeOrchestrationService;
import com.publicsapient.demo.service.RecipeSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecipeController {
    private final RecipeOrchestrationService orchestrationService;
    private final RecipeSearchService searchService;
    private final RecipeRepository repo;

    @PostMapping("/orchestrate/load")
    public ResponseEntity<?> load() {
        orchestrationService.loadAllRecipes();
        return ResponseEntity.accepted().body(Map.of("status","started"));
    }

    @GetMapping("/recipes")
    public ResponseEntity<Page<Recipe>> search(
            @RequestParam(value="query", required=false) String q,
            @RequestParam(value="page", defaultValue="0") int page,
            @RequestParam(value="size", defaultValue="20") int size) {

        List<Recipe> hits = searchService.search(q, page, size);
        Page<Recipe> p = new PageImpl<>(hits, PageRequest.of(page, size), hits.size());
        return ResponseEntity.ok(p);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getById(@PathVariable Long id){
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "id", id));
    }
}

