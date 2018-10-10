package blue.springframework.recipe.services;

import blue.springframework.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService
{
    Set<Recipe> getRecipes();
}
