package com.spring.recipe.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "recipe")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Recipe.class)
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "servings")
    private Integer servings;

    @Lob
    private String instructions;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ri_fid", referencedColumnName = "id")
    private Set<Ingredient> ingredients = new HashSet<>();

    public Recipe(String title, String type, Integer servings, String instructions,
                  Set<Ingredient> ingredients) {
        this.title = title;
        this.type = type;
        this.servings = servings;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }
}
