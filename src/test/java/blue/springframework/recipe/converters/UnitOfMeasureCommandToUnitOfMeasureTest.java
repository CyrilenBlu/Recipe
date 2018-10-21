package blue.springframework.recipe.converters;

import blue.springframework.recipe.commands.UnitOfMeasureCommand;
import blue.springframework.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final Long ID_VAL = new Long(1L);
    public static final String DESCRIPTION = "testDescription";
    UnitOfMeasureCommandToUnitOfMeasure converter;
    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void testNullObject()
    {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject()
    {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }


    @Test
    void convert() {
        //given
        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(ID_VAL);
        uomCommand.setDescription(DESCRIPTION);
        //when
        UnitOfMeasure uom = converter.convert(uomCommand);
        //then
        assertEquals(uom.getId(), ID_VAL);
        assertEquals(uom.getDescription(), DESCRIPTION);
    }
}