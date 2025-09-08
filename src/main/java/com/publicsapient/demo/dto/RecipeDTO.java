package com.publicsapient.demo.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RecipeDTO {

    private Long id; // keep for read/update cases

    @NotBlank
    private String name;

    private List<@NotBlank String> ingredients;

    private List<@NotBlank String> instructions;

    @Min(0)
    private Integer prepTimeMinutes;

    @Min(0)
    private Integer cookTimeMinutes;

    @Min(1)
    private Integer servings;

    @NotBlank
    private String difficulty;

    @NotBlank
    private String cuisine;

    @Min(0)
    private Integer caloriesPerServing;

    private List<@NotBlank String> tags;

    private Long userId;

    private String image;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double rating;

    @Min(0)
    private Integer reviewCount;

    private List<@NotBlank String> mealType;
}
