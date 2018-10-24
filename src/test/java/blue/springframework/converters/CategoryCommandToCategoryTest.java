package blue.springframework.converters;

import blue.springframework.commands.CategoryCommand;
import blue.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    public static final Long ID_VAL = new Long(1L);
    public static final String DESCRIPTION = "testDesc";
    CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject()
    {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert()
    {
        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VAL);
        categoryCommand.setDescription(DESCRIPTION);
        //when
        Category category = converter.convert(categoryCommand);
        //then
        assertEquals(category.getId(), ID_VAL);
        assertEquals(category.getDescription(), DESCRIPTION);
    }
}