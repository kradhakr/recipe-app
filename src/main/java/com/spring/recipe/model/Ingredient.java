package com.spring.recipe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "recipe")
@Entity
@Table(name = "ingredient")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Ingredient.class)
public class Ingredient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String description;

    private Double amount;

    @JsonIgnore
    @ManyToOne(cascade= CascadeType.ALL)
    private Recipe recipe;

    public Ingredient(String description, Double amount)
    {
        this.description = description;
        this.amount = amount;
    }
}
