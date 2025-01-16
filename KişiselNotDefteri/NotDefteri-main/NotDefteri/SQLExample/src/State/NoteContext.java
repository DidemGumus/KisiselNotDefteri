package State;

public class NoteContext {
    private NoteState state;

    public void setState(NoteState state) {
        this.state = state;
    }

    public NoteState getState() {
        return this.state;
    }

    public void applyState(int noteId) {
        if (state != null) {
            state.handle(noteId);
        } else {
            System.out.println("No state set for note ID: " + noteId);
        }
    }
}
