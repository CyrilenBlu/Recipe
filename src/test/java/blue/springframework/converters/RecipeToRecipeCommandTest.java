package blue.springframework.converters;

import blue.springframework.commands.NotesCommand;
import blue.springframework.commands.RecipeCommand;
import blue.springframework.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeToRecipeCommandTest {
    public static final Long ID_VAL = new Long(1L);
    public static final String DESCRIPTION = "descTest";
    public static final Integer PREPTIME = new Integer(15);
    public static final Integer COOKTIME = new Integer(10);
    public static final Integer SERVINGS = new Integer(3);
    public static final String SOURCE = "sourceTest";
    public static final String URL = "http://www.url.com/";
    public static final String DIRECTIONS = "1. test 2. bla 3. tokat";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Set<Ingredient> INGREDIENT_COMMAND_SET = new HashSet<>();
    public static final Set<Category> CATEGORY_COMMAND_SET = new HashSet<>();
    RecipeToRecipeCommand converter;
    Notes NOTES;
    NotesToNotesCommand notesConverter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeCommand(new NotesToNotesCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand());
        NOTES = new Notes();
        notesConverter = new NotesToNotesCommand();
    }

    @Test
    void testNullObject()
    {
        converter.convert(null);
    }

    @Test
    void testEmptyObject()
    {
        converter.convert(new Recipe());
    }

    @Test
    void convert() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(ID_VAL);
        recipe.setCookTime(COOKTIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setPrepTime(PREPTIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setCategories(CATEGORY_COMMAND_SET);
        recipe.setIngredients(INGREDIENT_COMMAND_SET);
        recipe.setNotes(NOTES);
        //when
        RecipeCommand recipeCommand = converter.convert(recipe);
        NotesCommand notesCommand = notesConverter.convert(NOTES);
        recipeCommand.setNotesCommand(notesCommand);
        //then
        assertEquals(recipeCommand.getId(), ID_VAL);
        assertEquals(recipeCommand.getCategoryCommands(), CATEGORY_COMMAND_SET);
        assertEquals(recipeCommand.getIngredients(), INGREDIENT_COMMAND_SET);
        assertEquals(recipeCommand.getCookTime(), COOKTIME);
        assertEquals(recipeCommand.getDescription(), DESCRIPTION);
        assertEquals(recipeCommand.getDirections(), DIRECTIONS);
        assertEquals(recipeCommand.getNotesCommand(), notesCommand);
        assertEquals(recipeCommand.getPrepTime(), PREPTIME);
        assertEquals(recipeCommand.getServings(), SERVINGS);
        assertEquals(recipeCommand.getUrl(), URL);
        assertEquals(recipeCommand.getSource(), SOURCE);
        Assertions.assertEquals(recipeCommand.getDifficulty(), DIFFICULTY);
    }
}