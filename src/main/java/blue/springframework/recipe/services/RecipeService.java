package blue.springframework.recipe.services;

import blue.springframework.recipe.commands.RecipeCommand;
import blue.springframework.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService
{
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
