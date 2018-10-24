package blue.springframework.services;

import blue.springframework.commands.RecipeCommand;
import blue.springframework.domain.Recipe;

import java.util.Set;

public interface RecipeService
{
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
