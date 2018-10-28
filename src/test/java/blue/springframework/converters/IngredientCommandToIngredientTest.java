package blue.springframework.converters;

import blue.springframework.commands.IngredientCommand;
import blue.springframework.commands.UnitOfMeasureCommand;
import blue.springframework.domain.Ingredient;
import blue.springframework.domain.UnitOfMeasure;
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
        ingredientCommand.setUom(uomCommand);
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