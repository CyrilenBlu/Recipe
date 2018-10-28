package blue.springframework.commands;

import blue.springframework.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions = "test";
    private Difficulty difficulty;
    private NotesCommand notesCommand;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categoryCommands = new HashSet<>();
}
