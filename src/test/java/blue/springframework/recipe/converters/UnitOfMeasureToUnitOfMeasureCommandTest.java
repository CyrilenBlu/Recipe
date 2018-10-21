package blue.springframework.recipe.converters;

import blue.springframework.recipe.commands.UnitOfMeasureCommand;
import blue.springframework.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitOfMeasureToUnitOfMeasureCommandTest {
    public static final Long ID_VAL = 1L;
    public static final String DESCRIPTION = "TestDESCRIPTION";
    UnitOfMeasureToUnitOfMeasureCommand converter;
    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void testNullObject()
    {
        converter.convert(null);
    }

    @Test
    void testEmptyObject()
    {
        converter.convert(new UnitOfMeasure());
    }

    @Test
    void convert() {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(ID_VAL);
        uom.setDescription(DESCRIPTION);
        //when
        UnitOfMeasureCommand uomCommand = converter.convert(uom);
        //then
        assertEquals(uomCommand.getId(), ID_VAL);
        assertEquals(uomCommand.getDescription(), DESCRIPTION);
    }
}