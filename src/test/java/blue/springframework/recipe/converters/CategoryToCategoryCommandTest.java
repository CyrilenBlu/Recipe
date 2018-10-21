package blue.springframework.recipe.converters;

import blue.springframework.recipe.commands.CategoryCommand;
import blue.springframework.recipe.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    public static final Long ID_VAL = new Long(1L);
    public static final String DESCRIPTION = "testDesc";
    CategoryToCategoryCommand converter;
    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void testNullObject()
    {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject()
    {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        //given
        Category category = new Category();
        category.setId(ID_VAL);
        category.setDescription(DESCRIPTION);
        //when
        CategoryCommand categoryCommand = converter.convert(category);
        //then
        assertEquals(categoryCommand.getId(), ID_VAL);
        assertEquals(categoryCommand.getDescription(), DESCRIPTION);
    }
}