package com.publicsapient.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Entity
@Table(name = "recipes")
@Data
@Indexed
public class Recipe {
    @Id
    private Long id; // DB primary key [12]

    @NotBlank
    @FullTextField
    @Column(nullable = false)
    private String name; // "Classic Margherita Pizza" [15]

    @ElementCollection
    @FullTextField
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient", nullable = false)
    private List<@NotBlank String> ingredients; // list of strings [3]

    @ElementCollection
    @CollectionTable(name = "recipe_instructions", joinColumns = @JoinColumn(name = "recipe_id"))
    @OrderColumn(name = "step_order")
    @Column(name = "step", columnDefinition = "TEXT", nullable = false)
    private List<@NotBlank String> instructions; // ordered steps [3]

    @Min(0)
    private Integer prepTimeMinutes; // 20 [6]

    @Min(0)
    private Integer cookTimeMinutes; // 15 [6]

    @Min(1)
    private Integer servings; // 4 [6]

    @NotBlank
    private String difficulty; // "Easy" [3]

    @NotBlank
    @FullTextField
    private String cuisine; // "Italian" [3]

    @Min(0)
    private Integer caloriesPerServing; // 300 [6]

    @ElementCollection
    @CollectionTable(name = "recipe_tags", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "tag")
    private List<@NotBlank String> tags; // ["Pizza","Italian"] [3]

    private Long userId; // 166 [3]

    @Column(length = 2048)
    private String image; // image URL [7][13][16]

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double rating; // 4.6 [6]

    @Min(0)
    private Integer reviewCount; // 98 [6]

    @ElementCollection
    @CollectionTable(name = "recipe_meal_types", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "meal_type")
    private List<@NotBlank String> mealType; // ["Dinner"] [3]
}

