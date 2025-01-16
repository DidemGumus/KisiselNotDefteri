package State;

import Model.NoteModel;

public class CompletedState implements NoteState {
    @Override
    public void handle(int noteId) {
        System.out.println("Note " + noteId + " is in Completed state.");

    }

    @Override
    public String getStateName() {
        return "Tamamlandı";
    }
}
