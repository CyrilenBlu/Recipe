package blue.springframework.recipe.converters;

import blue.springframework.recipe.commands.NotesCommand;
import blue.springframework.recipe.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotesToNotesCommandTest {
    public static final Long ID_VAL = new Long(1L);
    public static final String RECIPENOTES = "RecipeNTest";
    NotesToNotesCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    void testNullObject()
    {
        converter.convert(null);
    }

    @Test
    void testEmptyObject()
    {
        converter.convert(new Notes());
    }

    @Test
    void convert() {
        //given
        Notes notes = new Notes();
        notes.setId(ID_VAL);
        notes.setRecipeNotes(RECIPENOTES);
        //when
        NotesCommand notesCommand = converter.convert(notes);
        //then
        assertEquals(notesCommand.getId(), ID_VAL);
        assertEquals(notesCommand.getRecipeNotes(), RECIPENOTES);
    }
}