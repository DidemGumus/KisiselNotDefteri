package Strategy;

import Model.Note;
import java.util.List;

public class NoteFilterContext {
    private NoteFilterStrategy strategy;

    public void setStrategy(NoteFilterStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Note> filterNotes(List<Note> notes) {
        if (strategy == null) {
            throw new IllegalStateException("Filter strategy not set!");
        }
        return strategy.filter(notes);
    }
}
