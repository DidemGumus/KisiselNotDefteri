package Strategy;

import Model.Note;
import java.util.List;

public interface NoteFilterStrategy {
    List<Note> filter(List<Note> notes);
}
