package blue.springframework.recipe.converters;

import blue.springframework.recipe.commands.CategoryCommand;
import blue.springframework.recipe.commands.IngredientCommand;
import blue.springframework.recipe.commands.NotesCommand;
import blue.springframework.recipe.commands.RecipeCommand;
import blue.springframework.recipe.domain.Difficulty;
import blue.springframework.recipe.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeCommandToRecipeTest {
    public static final Long ID_VAL = new Long(1L);
    public static final String DESCRIPTION = "descTest";
    public static final Integer PREPTIME = new Integer(15);
    public static final Integer COOKTIME = new Integer(10);
    public static final Integer SERVINGS = new Integer(3);
    public static final String SOURCE = "sourceTest";
    public static final String URL = "http://www.url.com/";
    public static final String DIRECTIONS = "1. test 2. bla 3. tokat";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Set<IngredientCommand> INGREDIENT_COMMAND_SET = new HashSet<>();
    public static final Set<CategoryCommand> CATEGORY_COMMAND_SET = new HashSet<>();
    RecipeCommandToRecipe converter;
    NotesCommand NOTES_COMMAND;
    NotesCommandToNotes notesConverter;

    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
        notesConverter = new NotesCommandToNotes();
        NOTES_COMMAND = new NotesCommand();
    }

    @Test
    void testNullObject()
    {
        converter.convert(null);
    }

    @Test
    void testEmptyObject()
    {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setNotesCommand(new NotesCommand());
        converter.convert(recipeCommand);
    }

    @Test
    void convert() {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID_VAL);
        recipeCommand.setCookTime(COOKTIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setPrepTime(PREPTIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setCategoryCommands(CATEGORY_COMMAND_SET);
        recipeCommand.setIngredientCommands(INGREDIENT_COMMAND_SET);
        recipeCommand.setNotesCommand(NOTES_COMMAND);
        //when
        Recipe recipe = converter.convert(recipeCommand);
        recipe.setNotes(notesConverter.convert(NOTES_COMMAND));
        recipe.setDirections(DIRECTIONS);
        //then
        assertEquals(recipe.getId(), ID_VAL);
        assertEquals(recipe.getCategories(), CATEGORY_COMMAND_SET);
        assertEquals(recipe.getIngredients(), INGREDIENT_COMMAND_SET);
        assertEquals(recipe.getCookTime(), COOKTIME);
        assertEquals(recipe.getDescription(), DESCRIPTION);
        assertEquals(recipe.getDirections(), DIRECTIONS);
        assertEquals(recipe.getNotes(), notesConverter.convert(NOTES_COMMAND));
        assertEquals(recipe.getPrepTime(), PREPTIME);
        assertEquals(recipe.getServings(), SERVINGS);
        assertEquals(recipe.getUrl(), URL);
        assertEquals(recipe.getSource(), SOURCE);
        assertEquals(recipe.getDifficulty(), DIFFICULTY);
    }
}