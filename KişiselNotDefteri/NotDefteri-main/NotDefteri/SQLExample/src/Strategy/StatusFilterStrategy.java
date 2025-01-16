package Strategy;

import Model.Note;
import java.util.List;
import java.util.stream.Collectors;

public class StatusFilterStrategy implements NoteFilterStrategy {
    private String status;

    public StatusFilterStrategy(String status) {
        this.status = status;
    }

    @Override
    public List<Note> filter(List<Note> notes) {
        return notes.stream()
                .filter(note -> note.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
}
