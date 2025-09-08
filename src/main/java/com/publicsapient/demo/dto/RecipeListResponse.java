package com.publicsapient.demo.dto;


import lombok.Data;

import java.util.List;
@Data
public class RecipeListResponse {
    private List<RecipeDTO> recipes;
    private int total;
    private int skip;
    private int limit;
}

