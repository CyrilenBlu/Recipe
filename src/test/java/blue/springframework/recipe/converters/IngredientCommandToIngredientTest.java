package blue.springframework.recipe.converters;

import blue.springframework.recipe.commands.IngredientCommand;
import blue.springframework.recipe.commands.UnitOfMeasureCommand;
import blue.springframework.recipe.domain.Ingredient;
import blue.springframework.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientCommandToIngredientTest {
    public static final Long ID_VAL = new Long(1L);
    public static final BigDecimal AMOUNT = new BigDecimal(25.3);
    public static final String DESCRIPTION = "descTest";

    UnitOfMeasureCommand uomCommand;
    UnitOfMeasureCommandToUnitOfMeasure uomComToUom;
    IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        uomComToUom = new UnitOfMeasureCommandToUnitOfMeasure();
        uomCommand = new UnitOfMeasureCommand();
    }

    @Test
    void testNullObject()
    {
        converter.convert(null);
    }

    @Test
    void testEmptyObject()
    {
        converter.convert(new IngredientCommand());
    }

    @Test
    void convert() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VAL);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setUnitOfMeasureCommand(uomCommand);
        //when
        Ingredient ingredient = converter.convert(ingredientCommand);
        UnitOfMeasure uom = uomComToUom.convert(uomCommand);
        ingredient.setUom(uom);
        //then
        assertEquals(ingredient.getId(), ID_VAL);
        assertEquals(ingredient.getDescription(), DESCRIPTION);
        assertEquals(ingredient.getAmount(), AMOUNT);
        assertEquals(ingredient.getUom(), uom);
    }
}