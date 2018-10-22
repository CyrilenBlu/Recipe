package blue.springframework.recipe.converters;

import blue.springframework.recipe.commands.IngredientCommand;
import blue.springframework.recipe.commands.UnitOfMeasureCommand;
import blue.springframework.recipe.domain.Ingredient;
import blue.springframework.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientToIngredientCommandTest {
    public static final Long ID_VAL = new Long(1L);
    public static final BigDecimal AMOUNT = new BigDecimal(25.3);
    public static final String DESCRIPTION = "descTest";

    IngredientToIngredientCommand converter;
    UnitOfMeasureToUnitOfMeasureCommand uomToUomCom;
    UnitOfMeasure uom;

    @BeforeEach
    void setUp() {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        uomToUomCom = new UnitOfMeasureToUnitOfMeasureCommand();
        uom = new UnitOfMeasure();
    }

    @Test
    void testNullObject()
    {
        converter.convert(null);
    }

    @Test
    void testEmptyObject()
    {
        converter.convert(new Ingredient());
    }

    @Test
    void convert() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VAL);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setUom(uom);
        //when
        IngredientCommand ingredientCommand = converter.convert(ingredient);
        UnitOfMeasureCommand uomCommand = uomToUomCom.convert(uom);
        ingredientCommand.setUnitOfMeasureCommand(uomCommand);
        //then
        assertEquals(ingredientCommand.getId(), ID_VAL);
        assertEquals(ingredientCommand.getAmount(), AMOUNT);
        assertEquals(ingredientCommand.getDescription(), DESCRIPTION);
        assertEquals(ingredientCommand.getUnitOfMeasureCommand(), uomCommand);

    }
}