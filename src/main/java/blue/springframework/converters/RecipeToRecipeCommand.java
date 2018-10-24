package blue.springframework.converters;

import blue.springframework.commands.RecipeCommand;
import blue.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private NotesToNotesCommand notesToNotesCommand;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private CategoryToCategoryCommand categoryToCategoryCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand notesToNotesCommand, IngredientToIngredientCommand ingredientToIngredientCommand, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.notesToNotesCommand = notesToNotesCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null)
        {
            return null;
        }
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setNotesCommand(notesToNotesCommand.convert(source.getNotes()));
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        if (recipeCommand.getIngredientCommands() != null && recipeCommand.getIngredientCommands().size() > 0)
        {
            source.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredientCommands().add(ingredientToIngredientCommand.convert(ingredient)));
        }
        if (recipeCommand.getCategoryCommands() != null && recipeCommand.getCategoryCommands().size() > 0)
        {
            source.getCategories()
                    .forEach(category -> recipeCommand.getCategoryCommands().add(categoryToCategoryCommand.convert(category)));
        }

        return recipeCommand;
    }
}
