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
    public void deleteById(Long recipeId, Long idToDelete) {
        log.debug("deleting ingredient: " + idToDelete);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent())
        {
            Recipe recipe = recipeOptional.get();
            log.debug("found recipe!");

            Optional<Ingredient> ingredientOptional =
                    recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(idToDelete))
                    .findFirst();

            if (ingredientOptional.isPresent())
            {
                log.debug("found ingredient");
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        } else {
            log.debug("Recipe Id Not Found. ID: " + recipeId);
        }

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
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe
                                                            .getIngredients()
                                                            .stream()
                                                            .filter(recipeIngredients ->
                                                                    recipeIngredients.getId().equals(command.getId()))
                                                            .findFirst();

            //check by description.
            if (!savedIngredientOptional.isPresent())
            {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }


    }
}
