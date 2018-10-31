package blue.springframework.services;

import blue.springframework.commands.IngredientCommand;
import blue.springframework.converters.IngredientCommandToIngredient;
import blue.springframework.converters.IngredientToIngredientCommand;
import blue.springframework.domain.Ingredient;
import blue.springframework.domain.Recipe;
import blue.springframework.repositories.RecipeRepository;
import blue.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent())
        {
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                                                                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                                                                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                                                                .findFirst();

        if(!ingredientCommandOptional.isPresent())
        {
            //todo impl error handling
            log.error("Ingredient id: " + ingredientId);
        }
        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(command.getRecipeId());
        if (!optionalRecipe.isPresent())
        {
            //todo toss error if not found!
            log.error("Recipe not found for id: "  + command.getRecipeId());
            return new IngredientCommand();
        } else
        {
            Recipe recipe = optionalRecipe.get();

            Optional<Ingredient> optionalIngredient = recipe.getIngredients()
                    .stream()
                    .filter(ingredient
                            -> ingredient.getId()
                            .equals(command.getId()))
                    .findFirst();
            if (optionalIngredient.isPresent())
            {
                Ingredient ingredientFound = optionalIngredient.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException(
                                "UOM NOT FOUND"
                        )));//todo address this
            } else
            {
                //add new ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //todo check for fail.
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients()
                    .stream()
                    .filter(ingredient
                            -> ingredient.getId()
                            .equals(command.getId()))
                    .findFirst()
                    .get());
        }
    }
}
