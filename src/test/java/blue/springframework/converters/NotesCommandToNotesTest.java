package blue.springframework.converters;

import blue.springframework.commands.NotesCommand;
import blue.springframework.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotesCommandToNotesTest {
    public static final Long ID_VAL = new Long(1L);
    public static final String RECIPENOTES = "notesTest";
    NotesCommandToNotes converter;
    @BeforeEach
    void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    void testNullObject()
    {
        converter.convert(null);
    }

    @Test
    void testEmptyObject()
    {
        converter.convert(new NotesCommand());
    }

    @Test
    void convert() {
        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VAL);
        notesCommand.setRecipeNotes(RECIPENOTES);
        //when
        Notes notes = converter.convert(notesCommand);
        //then
        assertEquals(notes.getId(), ID_VAL);
        assertEquals(notes.getRecipeNotes(), RECIPENOTES);
    }
}