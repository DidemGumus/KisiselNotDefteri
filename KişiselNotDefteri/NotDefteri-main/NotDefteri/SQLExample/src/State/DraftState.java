package State;

import Model.NoteModel;

public class DraftState implements NoteState {
    @Override
    public void handle(int noteId) {
        System.out.println("Note " + noteId + " is in Draft state.");
    }

    @Override
    public String getStateName() {
        return "Taslak";
    }
}
