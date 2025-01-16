package State;

public interface NoteState {
    void handle(int noteId);
    String getStateName();
}
