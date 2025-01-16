package Strategy;

import Model.Note;
import java.util.List;
import java.util.stream.Collectors;

public class TitleFilterStrategy implements NoteFilterStrategy {
    private String keyword;

    public TitleFilterStrategy(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public List<Note> filter(List<Note> notes) {
        return notes.stream()
                .filter(note -> note.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
